package com.max256.morpho.common.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

	
	   public static void main(String[] args) {
		   
		   Long t1=System.nanoTime();
	        Random random = new Random();
	        List<Integer> numbers = new ArrayList<>();
	        for (int i = 0; i < 10000000; i++) {
	            numbers.add(random.nextInt(100000));
	        }
	        int result = calculate(numbers, 3);
	        System.out.println(result);
	        Long t2=System.nanoTime();
	        System.out.println((t2-t1)/1000000);
	    }

	    public static int calculate(List<Integer> numbers,int digit) {
	        List<Callable<Integer>> tasks = new ArrayList<>();
	        for (Integer x : numbers) {
	            tasks.add(() -> {
	                int count=0;
	                int y=x;
	                do {
	                    if (y % 10 == digit) {
	                        count++;
	                    }
	                    y /= 10;
	                } while (y > 0);
	                return count;
	            });
	        }
	        ExecutorService service = Executors.newFixedThreadPool(1);
	        int answer=0;
	        try {
	            List<Future<Integer>> results = service.invokeAll(tasks);
	            for (Future<Integer> result : results) {
	                try {
	                    answer+=result.get();
	                } catch (ExecutionException e) {
	                    e.printStackTrace();
	                }
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        return answer;
	    }
	
	
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Executor executor = Executors.newFixedThreadPool(10);   
		Runnable task = new Runnable() {   
		    @Override  
		    public void run() {   
		    	for(int i=1;i<=100;i++){
		    		 System.out.println("1 task over"+Thread.currentThread().getName());
		    	}
 
		    }   
		}; 
		Runnable task2 = new Runnable() {   
		    @Override  
		    public void run() {   
		    	for(int i=1;i<=100;i++){
		    		 System.out.println("2 task over"+Thread.currentThread().getName());
		    	}
 
		    }   
		}; 
		executor.execute(task); 
		executor.execute(task2); 
		
	}*/

}
