/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jhon
 */
public class regex {
    public static void main(String[] args){
        String regex = "[^A-z]";
           
            Pattern pattern = Pattern.compile(regex);
            Matcher mat = pattern.matcher("1");
            System.out.println(mat.find());
    }
}
