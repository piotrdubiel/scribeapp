package io.scribe.settings.account.state.generic;

public interface Lockable {

    Lockable NULL = new Lockable() {

        @Override
        public void lock() {
        }

        @Override
        public void unlock() {
        }
    };

    void lock();

    void unlock();
}
