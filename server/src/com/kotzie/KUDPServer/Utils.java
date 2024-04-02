package com.kotzie.KUDPServer;

import java.security.SecureRandom;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Utils {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	private Utils(){}
	
	public static boolean isSequenceNumberValid(int _current, int _new){
		return ((_new > _current) && (_new - _current <= Protocol.PACKET_HALF_SEQUENCE_NUMBER)) ||
			   ((_new < _current) && (_current - _new >= Protocol.PACKET_HALF_SEQUENCE_NUMBER));
	}
	
	/**
	 * @TODO: today, the Protocol only accept sequential reliable sequence numbers.
	 * First, this can change someday (although I think difficult)
	 * But, if I receive Reliable Packets newer than mine+1, shouldn't I buffer it and
	 * when I receive the one that's missing I can instant-read the buffered ones,
	 * so I don't need to ask them again? It may be a time-saver, but perhaps it should
	 * be somehow measured before any modifications.
	 */
	public static boolean isReliableSequenceNumberValid(int _current, int _new){
		return ((_new > _current) && (_new == (_current + 1)) && (_new - _current <= Protocol.PACKET_HALF_SEQUENCE_NUMBER)) ||
			   ((_new < _current) && (_new == 1) && (_current - _new >= Protocol.PACKET_HALF_SEQUENCE_NUMBER));
	}
	
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static String randomString( int len ){
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
}
