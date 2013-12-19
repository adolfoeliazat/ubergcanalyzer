import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;



public class TestParser {


	@Test
	public void testRegex() {
		final Pattern p = Pattern.compile("^go.*?Right");
		final Matcher m = p.matcher("goLeftRightRightDown");
		
		while(m.find()) {
			System.out.println(m.group());
		}
		
	}
	
	@Test
	public void test2() throws ParseException {
		final String minor = "2013-03-23T15:06:05.424+0100: 1,147: " +
				"[GC [PSYoungGen: 19843K->2558K(38208K)] "
				+ "19843K->2558K(125632K), 0,0121400 secs] [Times: user=0,03 sys=0,00, real=0,01 secs] ";
		
		final String full = "2013-03-25T13:53:36.739+0100: 8,434: " +
				"[Full GC (System) [PSYoungGen: 2494K->0K(38208K)] " +
				"[PSOldGen: 0K->2365K(87424K)] 2494K->2365K(125632K) " +
				"[PSPermGen: 12394K->12394K(25344K)], 0,0328860 secs] " +
				"[Times: user=0,03 sys=0,00, real=0,04 secs]";
		
		String s = full;
		//timestamp:
		
		
	//	Date data = DateFormat.getDateInstance().parse("2013-03-23T15:06:05");
			DateTimeFormatter parser = ISODateTimeFormat.dateTime();
			DateTime time = parser.parseDateTime("2013-03-25T13:53:36.739+0100");
			
			
			LocalTime l = new LocalTime(0, 0, 61, 0);
			System.out.println(l);

		
		/*
		List<String> l = devide(s, ' ', 1);
		final String timestamp = l.get(0);
		s = l.get(1);
		System.out.println(timestamp);
		
		//ellapsed time
		l = devide(s, ' ', 2);
		final String elappsed = l.get(0);
		s = l.get(1);
		System.out.println(elappsed);
		
		//nameray
		l = devide(s, '[', 1);
		final String name = l.get(0);
		s = l.get(1);
		System.out.println(name);
		*/
		
		
	//	final String full = "2013-03-25T13:53:36.739+0100: 8,434: [Full GC (System) [PSYoungGen: 2494K->0K(38208K)] " +
	//			"[PSOldGen: 0K->2365K(87424K)] 2494K->2365K(125632K) [PSPermGen: 12394K->12394K(25344K)], 0,0328860 secs] " +
	//			"[Times: user=0,03 sys=0,00, real=0,04 secs] ";
			
	}
	
	
	private List<String> devide(final String s, char sep, int moveForward)
	{
		final List<String> list = Lists.newArrayList();
		
		final int idx = s.indexOf(sep);
		list.add(s.substring(0, idx-1));
		list.add(s.substring(idx+moveForward));
		
		return list;
	}
	
	public void test() {
		final String text = "2013-03-23T15:06:05.424+0100: 1,147: [GC [PSYoungGen: 19843K->2558K(38208K)] "
				+ "19843K->2558K(125632K), 0,0121400 secs] [Times: user=0,03 sys=0,00, real=0,01 secs] ";
		
		
//		final String regex = "((^.*?):\\s)+"; //timestamp
		final String regex = "((^.*?):\\s)+";
		
		final Pattern p = Pattern.compile(regex);
		final Matcher matcher = p.matcher(text);
		
		//Assert.assertTrue(matcher.matches());
		
		while(matcher.find())
		{
			System.out.println("groupcount:" + matcher.groupCount());
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
}
