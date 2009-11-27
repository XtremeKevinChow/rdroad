package com.magic.app;

public class TestException {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("begin");
		try {
			throw new Exception();
			
		}catch(Exception ex){
			System.out.println("exception");
		}finally{
			System.out.println("finally");
		}
		System.out.println("end");
	}

}
