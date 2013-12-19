package de.morten.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;


/**
 * Event publisher to publish an event in the SAME thread. 
 *
 * @author Christian Bannes
 */
public class EventPublisher {
		private static final EventPublisher instance = new EventPublisher();
		private final ThreadLocal<EventBus> eventBus = new ThreadLocal<EventBus>();
		private final List<Object> registeredHandlers = new ArrayList<Object>();
		
		/**
		 * Prevents instantiation for the singleton
		 */
		private EventPublisher()  {
			//prevent instantiation
		}
		
		/**
		 * Returns the one and only instance
		 * 
		 * @return the {@link EventPublisher} instance
		 */
		public static EventPublisher instance() {
			return instance;
		}
		
		/**
		 * Publishes the event to all registered listeners
		 * 
		 * @param event the event to publish
		 */
		public void publishEvent(final Object event) {
			this.eventBus.get().post(event);
		}
		
		/**
		 * Because threads will be reused from a pool we have to clear the 
		 * {@link EventPublisher} for the current thead after the thread 
		 * is done. Typically this method will be called in a HttpServletFilter.
		 */
		public void reset() {
			for(final Object handler : registeredHandlers) {
				this.eventBus.get().unregister(handler);
			}
		}
		
		/**
		 * Registers an event handler
		 * 
		 * @param object the handler to register
		 */
		public void register(final Object object) {
			this.eventBus.get().register(object);
		}
		
		/**
		 * Returns the underlying event bus for the current thread.
		 * 
		 * @return the event bus for the current thread
		 */
		public EventBus getEventBus() {
			return this.eventBus.get();
		}
}
