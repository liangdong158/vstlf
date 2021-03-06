/************************************************************************
 MIT License

 Copyright (c) 2010 University of Connecticut

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
***********************************************************************/

package edu.uconn.vstlf.database.perst;

import org.garret.perst.TimeSeries.Block;
import org.garret.perst.TimeSeries.Tick;
public class PerstDataBlock extends Block {
	
	static final int SIZE = 4096;
	PerstDataPoint[] _points;
	
	public PerstDataBlock(){
		//_points = new PerstDataPoint[SIZE];
	}

	@Override
	public Tick[] getTicks() {
		if (_points == null) { 
            _points = new PerstDataPoint[SIZE];
            for (int i = 0; i < SIZE; i++) { 
                _points[i] = new PerstDataPoint();
            }
        }
        return _points;
	}
	public boolean equals(Object o) {
		if (o instanceof PerstDataBlock) {
			PerstDataBlock pb = (PerstDataBlock)o;
			if (_points.length != pb._points.length) return false;
			boolean eq = true;
			for(int k=0;eq && k < _points.length;k++)
				eq = _points[k].equals(pb._points[k]);
			return eq;
		} else return false;
	}
	public int hashCode() { return _points.length;}
}
