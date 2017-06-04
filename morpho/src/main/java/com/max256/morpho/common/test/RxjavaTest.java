/*package com.max256.morpho.common.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

public class RxjavaTest {

	public static void main(String[] args) {

		demo2();

	}

	static void demo2() {
			Observable.range(1, 2000)
			        .scan(new Func2<Integer, Integer, Integer>() {
			         
			            public Integer call(Integer integer, Integer integer2) {
			            	System.out.println("scan Func2 call() "+System.nanoTime()+
					        		"线程"+Thread.currentThread().getId()+"数据"+integer+"");
			                return integer + integer2;
			            }
			        }) .filter(new Func1<Integer, Boolean>() {
			           
			            public Boolean call(Integer integer) {
			            	System.out.println("filter Func1 call() "+System.nanoTime()+
					        		"线程"+Thread.currentThread().getId()+"数据"+integer+"");
			                return integer>1900;
			            }
			        })
			       // .observeOn(Schedulers.newThread())
			        //.subscribeOn(Schedulers.newThread())
			        .take(100)
			        .subscribe(new Action1<Integer>() {
			    
			    public void call(Integer integer) {
			        System.out.println("当前时间 "+System.nanoTime()+
			        		"线程"+Thread.currentThread().getId()+"数据"+integer+"");
			        
			    }
			});


	}
	static void demo3() {
		List<Student> Students = new ArrayList<Student>();
		Students.add(new Student("中粮·海景壹号", "中粮海景壹号新出大平层！总价4500W起"));
		Students.add(new Student("竹园新村", "满五唯一，黄金地段"));
		Students.add(new Student("中粮·海景壹号", "毗邻汤臣一品"));
		Students.add(new Student("竹园新村", "顶层户型，两室一厅"));
		Students.add(new Student("中粮·海景壹号", "南北通透，豪华五房"));
		Observable<GroupedObservable<String, Student>> groupByCommunityNameObservable = Observable.from(Students)
				.groupBy(new Func1<Student, String>() {

					public String call(Student Student) {
						return Student.phone;
					}
				});

		Observable.concat(groupByCommunityNameObservable).subscribe(new Action1<Student>() {

			public void call(Student house) {
				System.out.println("小区:" + house.name + "; 房源描述:" + house.phone);
			}
		});

	}
	public static void demo4(){
		Observable.just(2, 1, 2, 2, 3, 4, 3, 4, 5, 5)
        .distinct()
        .subscribe(
        		new Action1<Integer>() {
		            public void call(Integer i) {
		                System.out.print(i + " ");
		            }
        });
		
	}
	public static void demo5(){
		Observable.just(2, 1, 2, 2, 3, 4, 3, 4, 5, 5)
        .distinctUntilChanged()
        .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.print(i + " ");
            }
        });

	}
	public static void demo6(){
		final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
		Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
		        .map(new Func1<Long, String>() {
		      
		            public String call(Long position) {
		                return letters[position.intValue()];
		            }
		        }).take(letters.length);

		Observable<Long> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

		Observable.merge(letterSequence, numberSequence)
		        .subscribe(new Observer<Serializable>() {
		            @Override
		            public void onCompleted() {
		                System.exit(0);
		            }

		            @Override
		            public void onError(Throwable e) {
		                System.out.println("Error:" + e.getMessage());
		            }

		            @Override
		            public void onNext(Serializable serializable) {
		                System.out.print(serializable.toString()+" ");
		            }
		        });   
		
	}

}
*/