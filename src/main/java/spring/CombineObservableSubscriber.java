package spring;


import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.Arrays;
import java.util.List;

@Service
public class CombineObservableSubscriber {

    public void combine() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Emits string");
                        sub.onCompleted();
                    }
                }
        );// Observable emits "Hello, world!"

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error!");
            }
        };//Subscriber to consume the data

        //hook them up
        myObservable.subscribe(mySubscriber);
    }

    // simpler one
    public void combine1() {
        //        Blocks of reactive code are Observables and Subscribers
        //        Observable emits items; a Subscriber consumes those items.
        //        An Observable may emit any number of items -- infinity --
        //        An Observable lazy emitting!!!  ->

        // simple version emit one item!
        Observable<String> myObservable =
                Observable.just("Emits string");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

//        myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
        myObservable.subscribe(onNextAction);
    }

    // simpler one
    public void combine2() {
        Observable<String> myObservable =
                Observable.just("Emits string");

        myObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    // simpler one
    public void combine3() {
        Observable<String> myObservable =
                Observable.just("Emits string");

        myObservable.subscribe(s -> System.out.println(s));
    }

    //  Subscribers are supposed to be the thing that reacts, not the thing that mutates.
    public void transform() {

        Observable.just("Emits string")
                .map(s -> s + " - and transform")
                .subscribe(s -> System.out.println(s));
    }

    public void transform1() {

        Observable.just("Emits string")
                .map(s -> s + " - and transform")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.println(s));
    }

//  Key idea #1: Observable and Subscriber can do anything.
    /*
    Observable : {database query, click, stream of bytes read from the internet, }
    Subscriber : {displaying on the screen, click reacting, write it to the disk, }
    * */

//    We can model almost every system

    private Observable<List<String>> query(String text) {
        List<String> list = Arrays.asList(new String[]{"url1", "url2", "url3"});
        List<String> list2 = Arrays.asList(new String[]{"url21", "url22", "url23"});
        return Observable.just(list, list2);
    }


    private Observable<String> query1(String text) {
        return Observable.from(new String[]{"url1", "url2", "url3"});
    }

    public void searchUrl() {

        query("Wis").subscribe(urls -> {
            for (String url : urls) {
                System.out.println(url);
            }
        });
    }

    //I've gotten rid of the for-each loop, but the resulting code is a mess.
    public void searchUrl1() {
        query("Wis").subscribe(urls -> {
            Observable.from(urls).
                    subscribe(u -> System.out.println(u));
        });
    }

    public void searchUrl2() {
        query("Wis")
                .flatMap(urls -> Observable.from(urls))
                .subscribe(u -> System.out.println(u));
    }
}
