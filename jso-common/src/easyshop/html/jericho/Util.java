// Jericho HTML Parser - Java based library for analysing and manipulating HTML
// Version 1.5
// Copyright (C) 2004 Martin Jericho
// http://jerichohtml.sourceforge.net/
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// http://www.gnu.org/copyleft/lesser.html
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package easyshop.html.jericho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * This class contains miscellaneous utility methods not directly associated with the HTML Parser library.
 */
public final class Util {
	private static final int BUFFER_SIZE=1024;
	private static final String CSVNewLine=System.getProperty("line.separator");

	private Util() {}

	public static String getString(Reader reader) throws IOException {
		if (reader==null) return null;
		BufferedReader in=new BufferedReader(reader,BUFFER_SIZE);
		int charsRead;
		char[] copyBuffer=new char[BUFFER_SIZE];
		StringBuffer sb=new StringBuffer();
		while ((charsRead=in.read(copyBuffer,0,BUFFER_SIZE))!=-1)
			sb.append(copyBuffer,0,charsRead);
		in.close();
		return sb.toString();
	}

  public static void outputCSVLine(Writer writer, String[] values) throws IOException {
  	for (int i=0; i<values.length;) {
			String value=values[i];
  		if (value!=null) {
				if (value==FormFields.ColumnTrue || value==FormFields.ColumnFalse) {
					writer.write(value); // assumes neither ColumnTrue or ColumnFalse contain double quotes.
				} else {
		 			writer.write('"');
					outputValueEscapeQuotes(writer,value);
					writer.write('"');
				}
			}
			if (++i!=values.length) writer.write(',');
  	}
		writer.write(CSVNewLine);
  }

  private static void outputValueEscapeQuotes(Writer writer, String text) throws IOException {
		for (int i=0; i<text.length(); i++) {
			char ch=text.charAt(i);
			writer.write(ch);
			if (ch=='"') writer.write(ch);
		}
  }

	// use this method until we can replace with java 1.5 StringBuffer.append(CharSequence s)
	static StringBuffer appendTo(StringBuffer sb, CharSequence s) {
		return appendTo(sb,s,0,s.length());
	}
	// use this method until we can replace with java 1.5 StringBuffer.append(CharSequence s, int begin, int end)
	static StringBuffer appendTo(StringBuffer sb, CharSequence s, int start, int end) {
		while (start<end) {
			sb.append(s.charAt(start));
			start++;
		}
		return sb;
	}
	// use this method until we can replace with java 1.5 Writer.append(CharSequence s)
	static Writer appendTo(Writer writer, CharSequence s) throws IOException {
		return appendTo(writer,s,0,s.length());
	}
	// use this method until we can replace with java 1.5 Writer.append(CharSequence s, int begin, int end)
	static Writer appendTo(Writer writer, CharSequence s, int start, int end) throws IOException {
		while (start<end) {
			writer.write(s.charAt(start));
			start++;
		}
		return writer;
	}
}
