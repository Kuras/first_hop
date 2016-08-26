package spring;


import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

@Service
public class CombineObservableSubscriber {

    public void combine() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call( Subscriber<? super String> sub ) {
                        sub.onNext( "Emits string" );
                        sub.onCompleted();
                    }
                }
        );// Observable emits "Hello, world!"

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext( String s ) {
                System.out.println( s );
            }

            @Override
            public void onCompleted() {
                System.out.println( "Completed!" );
            }

            @Override
            public void onError( Throwable e ) {
                System.out.println( "Error!" );
            }
        };//Subscriber to consume the data

        //hook them up
        myObservable.subscribe( mySubscriber );
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

}
