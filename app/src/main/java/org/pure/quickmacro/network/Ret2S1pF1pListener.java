package org.pure.quickmacro.network;

public interface Ret2S1pF1pListener<Sp, Fp> {
    void onSuccess(Sp sp);

    void onFailed(Fp fp);
}
