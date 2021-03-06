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

package edu.uconn.vstlf.shutil;

import javax.swing.UIManager;
import java.util.Date;
import java.util.TimeZone;

import edu.uconn.vstlf.config.Items;
import edu.uconn.vstlf.data.message.DummyMsgHandler;
import edu.uconn.vstlf.data.message.MessageCenter;
import edu.uconn.vstlf.data.message.RealTimeMsgHandler;
import edu.uconn.vstlf.data.message.VSTLFMsgLogger;
import edu.uconn.vstlf.gui.IsoVstlfGui;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.*;

public class RunGUI {

	static String _USAGE = "usage:\n\tjava -jar uconn-vstlf.jar run-gui <currentDataFile> <24hrDataFile>"+
						   		  "\n\tjava -jar uconn-vstlf.jar run-gui <currentDataFile> <24hrDataFile> \"<testDate yyyy/MM/dd - HH:mm:ss>\" <clockInterval>"+
						   	   	  "\n\tjava -jar uconn-vstlf.jar run-gui <currentDataFile> <24hrDataFile> \"<testDate yyyy/MM/dd - HH:mm:ss>\" \"<testDate yyyy/MM/dd - HH:mm:ss>\" <clockInterval>";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date time = null, edtime = null;
		int rate = 4000;
		String histFile = null, currFile = null;
		if (args.length != 2 && args.length != 4 && args.length != 5) { //check # of args
			System.out.println(_USAGE);
			return;
		}
		try{										//assign args
			currFile = args[0];
			histFile = args[1];
			if(!new File(currFile).exists())
				throw new FileNotFoundException(currFile);
			if(!new File(histFile).exists())
				throw new FileNotFoundException(histFile);
			if(args.length == 4){
				rate = Integer.parseInt(args[3]);
				if(4000%rate != 0)
					throw new NumberFormatException();
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone(Items.get(Items.TimeZone)));
				time = df.parse(args[2]);
			}
			else if (args.length == 5){
				rate = Integer.parseInt(args[4]);
				if(4000%rate != 0)
					throw new NumberFormatException();
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone(Items.get(Items.TimeZone)));
				time = df.parse(args[2]);
				edtime = df.parse(args[3]);
			}
		}
		catch(ParseException e){
			System.out.println("Test date should be of the form \"yyyy/MM/dd - HH:mm:ss\"");
			return;
		}
		catch(NumberFormatException e){
			System.out.println("Specified clock rate is not valid. (Should be a valid divisor of 4000)");
			return;
		}
		catch(FileNotFoundException e){
			System.out.println("'" + e.getMessage() + "' does not refer to real file.");
			return;
		}
	    try {
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	IsoVstlfGui frame = new IsoVstlfGui(true, ".vstlf", args[0], args[1] );
	    	frame.setTestTime(time);
	    	frame.setClockRate(rate);
	        frame.init();
	    	if (edtime != null) frame.setTestEndTime(edtime);

	        MessageCenter.getInstance().setHandler(new RealTimeMsgHandler(frame, new VSTLFMsgLogger("vstlf.log", new DummyMsgHandler())));
	        MessageCenter.getInstance().init();
	        
	    } catch (Exception e) {
	        System.out.println(e.toString());
	        e.printStackTrace();
	        return;
	    }
	}

}
