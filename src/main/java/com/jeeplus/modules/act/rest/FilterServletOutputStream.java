package com.jeeplus.modules.act.rest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
//import javax.servlet.WriteListener;
import javax.servlet.WriteListener;

public class FilterServletOutputStream extends ServletOutputStream {

    private DataOutputStream stream;
   // private WriteListener writeListener;

    public FilterServletOutputStream(OutputStream output) {
        stream = new DataOutputStream(output);
    }

    public void write(int b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        stream.write(b, off, len);
    }

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}

	

//    @Override
//    public void setWriteListener(WriteListener writeListener) {
//        this.writeListener = writeListener;
//    }
//
//    @Override
//    public boolean isReady() {
//        return true;
//    }
}