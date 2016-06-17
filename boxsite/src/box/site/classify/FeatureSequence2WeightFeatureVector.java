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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

// This class does not insist on getting its own Alphabet because it can rely on getting
// it from the FeatureSequence input.
/**
 * Convert the data field from a feature sequence to a feature vector.
 */
public class FeatureSequence2WeightFeatureVector extends Pipe implements Serializable
{
	boolean binary;
	JSONObject json;

	public FeatureSequence2WeightFeatureVector (boolean binary,int keyid)
	{
		this.binary = binary;
		String content = FileUtil.readFile("data/training/"+keyid+".keysweight");
		if (content!=null&&content.trim().length()>0){
			json = JSON.parseObject(content);
		}
		int t = 10;
	}

	public FeatureSequence2WeightFeatureVector (int keyid)
	{
		this (false,keyid);
	}
	
	
	public Instance pipe (Instance carrier)
	{
		FeatureSequence fs = (FeatureSequence) carrier.getData();
		FeatureVector vector = new FeatureVector (fs, binary);
		for (String key:json.keySet()){
			int idx = vector.location(key);
			if (idx>0){
				int weight = json.getIntValue(key);
				double vv = vector.value(idx);
				if (vv>0){
				 vv *= weight;
				 vector.setValue(idx, vv);
				}
			}
		}
		carrier.setData(vector);
		return carrier;
	}

	// Serialization 
	
	private static final long serialVersionUID = 1;
	private static final int CURRENT_SERIAL_VERSION = 1;
	
	private void writeObject (ObjectOutputStream out) throws IOException {
		out.writeInt (CURRENT_SERIAL_VERSION);
		out.writeBoolean (binary);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
		int version = in.readInt ();
		if (version > 0)
			binary = in.readBoolean();
	}
}
