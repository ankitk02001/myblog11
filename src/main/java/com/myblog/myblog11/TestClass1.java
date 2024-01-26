package com.myblog.myblog11;


import org.springframework.data.domain.Sort;

public class TestClass1 {
   public static void main(String[] args) {
      int x = 40;
      int y = 20;

      int max = (x > y) ? x : y;
      System.out.println((Sort.Direction.ASC.name()));
      System.out.println("The maximum value is: " + max);
   }
   }

