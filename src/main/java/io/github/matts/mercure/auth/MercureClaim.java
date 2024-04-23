package io.github.matts.mercure.auth;

import java.util.ArrayList;
import java.util.List;

public class MercureClaim {
    private List<String> subscribe;
    private List<String> publish;

    public MercureClaim(List<String> subscribe, List<String> publish) {
        this.subscribe = subscribe;
        this.publish = publish;
    }

    public MercureClaim(List<String> subscribe) {
        this.subscribe = subscribe;
        this.publish = new ArrayList<>();
    }

    public List<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe) {
        this.subscribe = subscribe;
    }

    public List<String> getPublish() {
        return publish;
    }

    public void setPublish(List<String> publish) {
        this.publish = publish;
    }
}
