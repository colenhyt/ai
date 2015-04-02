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

import java.io.IOException;
import java.io.Writer;

/**
 * Implements an {@link IOutputSegment} whose content is a <code>CharSequence</code>.
 */
public final class StringOutputSegment implements IOutputSegment {
	private int begin;
	private int end;
	private CharSequence text;

	/**
	 * Constructs a new <code>StringOutputSegment</code> with the specified begin and end positions and the specified content.
	 * @param begin  the position in the <code>OutputDocument</code> where this output segment begins.
	 * @param end  the position in the <code>OutputDocument</code> where this output segment ends.
	 * @param text  the textual content of the new output segment, or <code>null</code> if no content.
	 */
	public StringOutputSegment(int begin, int end, CharSequence text) {
		this.begin=begin;
		this.end=end;
		this.text=text;
	}

	/**
	 * Constructs a new StringOutputSegment</code> with the same span as the specified {@link Segment}.
	 * @param segment  a segment defining the beginning and ending positions of the new output segment.
	 * @param text  the textual content of the new output segment, or <code>null</code> if no content.
	 */
	public StringOutputSegment(Segment segment, CharSequence text) {
		begin=segment.begin;
		end=segment.end;
		this.text=text;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public void output(Writer writer) throws IOException {
		if (text!=null) Util.appendTo(writer,text);
	}

	public String toString() {
		return text.toString();
	}

	public String getDebugInfo() {
		return "("+begin+','+end+"):"+(text==null ? "null" : "\""+text+'"');
	}
}
