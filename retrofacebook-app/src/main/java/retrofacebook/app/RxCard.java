/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package retrofacebook.app;

import android.support.annotation.Nullable;

import rx.Observable;
import retrofacebook.*;

public class RxCard {
    @Nullable
    public Observable<String> icon = Observable.empty();
    @Nullable
    public Observable<String> text1 = Observable.empty();
    @Nullable
    public Observable<String> message = Observable.empty();
    @Nullable
    public Observable<String> image = Observable.empty();
    @Nullable
    public Observable<Comment> comments = Observable.empty();
    @Nullable
    public Observable<Struct> like = Observable.empty();
    @Nullable
    public Observable<Struct> unlike = Observable.empty();
    @Nullable
    public Observable<Boolean> liked = Observable.empty();
    @Nullable
    public Observable<Integer> likeCount = Observable.empty();
}
