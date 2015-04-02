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
 * Implements an {@link IOutputSegment} whose content is a string of spaces with the same length as the segment.
 * <p>
 * This class is used internally to implement the functionality available through the
 * {@link Source#ignoreWhenParsing(int begin, int end)} method.
 * <p>
 * It is included in the public API in the unlikely event it has other practical uses
 * for the developer.
 */
public final class BlankOutputSegment implements IOutputSegment {
	private int begin;
	private int end;

	/**
	 * Constructs a new <code>BlankOutputSegment</code> with the specified begin and end positions.
	 * @param begin  the position in the {@link OutputDocument} where this <code>OutputSegment</code> begins.
	 * @param end  the position in the {@link OutputDocument} where this <code>OutputSegment</code> ends.
	 */
	public BlankOutputSegment(int begin, int end) {
		this.begin=begin;
		this.end=end;
	}

	/**
	 * Constructs a new <code>BlankOutputSegment</code> with the same span as the specified {@link Segment}.
	 * @param segment  a {@link Segment} defining the begin and end character positions of the new <code>OutputSegment</code>.
	 */
	public BlankOutputSegment(Segment segment) {
		this(segment.getBegin(),segment.getEnd());
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public void output(Writer writer) throws IOException {
		for (int i=begin; i<end; i++) writer.write(' ');
	}

	public String toString() {
		StringBuffer sb=new StringBuffer(end-begin);
		for (int i=begin; i<end; i++) sb.append(' ');
		return sb.toString();
	}

	public String getDebugInfo() {
		return "("+begin+','+end+')';
	}
}
