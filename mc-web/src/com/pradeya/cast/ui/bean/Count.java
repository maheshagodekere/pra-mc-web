package com.pradeya.cast.ui.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Count implements Serializable{
public static int count=0;
public Count() {
	count++;
}
public static void main(String[] args) {
	Count c1 = new Count();
	Count c2 = new Count();
	System.out.println(c1);
	System.out.println(count);
}
@Override
	public String toString() {
	String s = Integer.toString(count);
return s;
	}

}
