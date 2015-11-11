package com.stopfan.sigmalight.core.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by denys on 10/19/15.nts4.google.com/glm/mmap/api 0x99e6744e NORMAL 19
 10-20 16:07:58.625 16937-18586/com.it4medicine.nm D/Volley: [22246] a.a: HTTP response for reques
 */
public class RxUtils {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }
}

