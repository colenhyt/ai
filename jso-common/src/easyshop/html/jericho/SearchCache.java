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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

final class SearchCache {
	private static final String START_TAG_PREFIX="ST";
	private static final String END_TAG_PREFIX="ET";
	private static final String ELEMENT_PREFIX="E";
	private static final String TAG_PREFIX="T";

	private final HashMap cache=new HashMap();

	public List getTagList() {
		if (cache.isEmpty()) return Collections.EMPTY_LIST;
		ArrayList list=new ArrayList(cache.size());
		for (Iterator i=cache.values().iterator(); i.hasNext();) {
			Object segment=i.next();
			if (segment==StartTag.CACHED_NULL || segment==EndTag.CACHED_NULL) continue;
			if (segment instanceof Element) {
				Element element=(Element)segment;
				list.add(element.getStartTag());
				list.add(element.getEndTag());
			} else {
				list.add(segment); // This should always be a Tag.
			}
		}
		Collections.sort(list);
		// now remove any duplicates:
		Iterator i=list.iterator();
		Tag lastTag=(Tag)i.next();
		while(i.hasNext()) {
			Tag tag=(Tag)i.next();
			if (tag.getBegin()==lastTag.getBegin()) i.remove();
			lastTag=tag;
		}
		return list;
	}

	public static String getStartTagKey(int searchStartPos, String name, boolean previous) {
		// Note name may be null
		if (name==Tag.SERVER_MASON_NAMED_BLOCK) name="%named>"; // arbitrary name to distinguish from Tag.SERVER_COMMON
		return getKey(START_TAG_PREFIX,searchStartPos,name,previous);
	}
	public StartTag getStartTag(String key) {
		return (StartTag)cache.get(key);
	}
	public void setStartTag(String key, StartTag startTag) {
		cache.put(key, startTag==null ? StartTag.CACHED_NULL : startTag);
	}

	public static String getEndTagKey(int searchStartPos, String name, boolean previous) {
		// Note name is never null so this can't conflict with getEndTagKey(int pos)
		return getKey(END_TAG_PREFIX,searchStartPos,name,previous);
	}
	public static String getEndTagKey(int forwardSearchStartPos) {
		return ELEMENT_PREFIX+forwardSearchStartPos;
	}
	public EndTag getEndTag(String key) {
		return (EndTag)cache.get(key);
	}
	public void setEndTag(String key, EndTag endTag) {
		cache.put(key, endTag==null ? EndTag.CACHED_NULL : endTag);
	}

	public static String getElementKey(StartTag startTag) {
		return END_TAG_PREFIX+startTag.begin;
	}
	public Element getElement(String key) {
		return (Element)cache.get(key);
	}
	public void setElement(String key, Element element) {
		cache.put(key,element);
	}

	public static String getTagKey(int atPos) {
		return TAG_PREFIX+atPos;
	}
	public Tag getTag(String key) {
		return (Tag)cache.get(key);
	}
	public void setTag(String key, Tag tag) {
		cache.put(key, tag==null ? EndTag.CACHED_NULL : tag);
	}

	private static String getKey(String prefix, int pos, String name, boolean previous) {
		StringBuffer sb=new StringBuffer(prefix);
		sb.append(pos);
		if (name!=null) sb.append(name);
		if (previous) sb.append('<');
		return sb.toString();
	}
}

