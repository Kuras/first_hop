package spring;


import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;

@Service
public class CombineObservableSubscriber {

    public void combine( ){
        Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call( Subscriber<? super String> sub) {
                    sub.onNext("Emits string");
                    sub.onCompleted();
                }
            }
        );// Observable emits "Hello, world!"

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { System.out.println(s); }

            @Override
            public void onCompleted() { System.out.println("Completed!"); }

            @Override
            public void onError(Throwable e) { System.out.println("Error!"); }
        };//Subscriber to consume the data

        //hook them up
        myObservable.subscribe(mySubscriber);
    }

    // simpler one
    public void combine1( ){
        Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call( Subscriber<? super String> sub) {
                    sub.onNext("Emits string");
                    sub.onCompleted();
                }
            }
        );// Observable emits "Hello, world!"

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { System.out.println(s); }

            @Override
            public void onCompleted() { System.out.println("Completed!"); }

            @Override
            public void onError(Throwable e) { System.out.println("Error!"); }
        };//Subscriber to consume the data

        //hook them up
        myObservable.subscribe(mySubscriber);
    }

}
