/* Copyright (C) 2002 Univ. of Massachusetts Amherst, Computer Science Dept.
   This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
   http://www.cs.umass.edu/~mccallum/mallet
   This software is provided under the terms of the Common Public License,
   version 1.0, as published by http://www.opensource.org.  For further
   information, see the file `LICENSE' included with this distribution. */




/** 
   @author Andrew McCallum <a href="mailto:mccallum@cs.umass.edu">mccallum@cs.umass.edu</a>
 */

package box.site.classify;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import cc.mallet.extract.StringSpan;
import cc.mallet.extract.StringTokenization;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.types.Instance;
import cc.mallet.types.SingleInstanceIterator;
import cc.mallet.types.TokenSequence;
import cc.mallet.util.CharSequenceLexer;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

/**
 *  Pipe that tokenizes a character sequence.  Expects a CharSequence
 *   in the Instance data, and converts the sequence into a token
 *   sequence using the given regex or CharSequenceLexer.  
 *   (The regex / lexer should specify what counts as a token.)
 */
public class CharSequence2GBKTokenSequence extends Pipe implements Serializable
{
	CharSequenceLexer lexer;
	JiebaSegmenter segmenter;
	
	public CharSequence2GBKTokenSequence (CharSequenceLexer lexer)
	{
		this.lexer = lexer;
		segmenter = new JiebaSegmenter();
	}

	public CharSequence2GBKTokenSequence (String regex)
	{
		this.lexer = new CharSequenceLexer (regex);
		segmenter = new JiebaSegmenter();
	}

	public CharSequence2GBKTokenSequence (Pattern regex)
	{
		this.lexer = new CharSequenceLexer (regex);
		segmenter = new JiebaSegmenter();
	}

	public CharSequence2GBKTokenSequence ()
	{
		this (new CharSequenceLexer());
	}

	public Instance pipe (Instance carrier)
	{
		CharSequence string = (CharSequence) carrier.getData();
		lexer.setCharSequence (string);
		List<SegToken> segToken = segmenter.process(string.toString(), SegMode.INDEX);
		TokenSequence ts = new StringTokenization (string);
		for (SegToken token:segToken){
			String w = token.word;
			if (w==null||w.trim().length()<=0) continue;
			
			ts.add (new StringSpan (string, token.startOffset, token.endOffset));
		}
		carrier.setData(ts);
		return carrier;
	}

	public static void main (String[] args)
	{
		try {
			for (int i = 0; i < args.length; i++) {
				Instance carrier = new Instance (new File(args[i]), null, null, null);
				SerialPipes p = new SerialPipes (new Pipe[] {
					new Input2CharSequence (),
					new CharSequence2GBKTokenSequence(new CharSequenceLexer())});
				carrier = p.newIteratorFrom (new SingleInstanceIterator(carrier)).next();
				TokenSequence ts = (TokenSequence) carrier.getData();
				System.out.println ("===");
				System.out.println (args[i]);
				System.out.println (ts.toString());
			}
		} catch (Exception e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}

	// Serialization 
	
	private static final long serialVersionUID = 1;
	private static final int CURRENT_SERIAL_VERSION = 0;
	
	private void writeObject (ObjectOutputStream out) throws IOException {
		out.writeInt(CURRENT_SERIAL_VERSION);
		out.writeObject(lexer);
		out.writeObject(segmenter);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
		int version = in.readInt ();
		lexer = (CharSequenceLexer) in.readObject();
		segmenter = (JiebaSegmenter)in.readObject();
	}


	
}
