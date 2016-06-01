/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.shelly;

import org.junit.Test;

import xiaofei.library.shelly.annotation.DominoTarget;
import xiaofei.library.shelly.action.Action0;
import xiaofei.library.shelly.action.Action1;
import xiaofei.library.shelly.action.TargetAction;

/**
 * Created by Xiaofei on 16/5/30.
 */
public class Test01 {

    private static class A {
        private int i;
        A(int i) {
            this.i = i;
        }
        @DominoTarget("target1")
        public void f(String s) {
            System.out.println("A " + i + " f " + s);
        }

        public void g(String s) {
            System.out.println("A " + i + " g " + s);
        }
    }
    @Test
    public void case01() {
        Shelly.register(new A(1));
        Shelly.register(new A(2));
        Shelly.createDomino("case01")
                .target(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Target action0");
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("Target action1 " + input);
                    }
                })
                .then(A.class, "target1")
                .then(A.class, new TargetAction<A>() {
                    @Override
                    public void call(A a, Object input) {
                        a.g((String) input);
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("After map : " + input);
                    }
                })
                .then(A.class, "target1")
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("After map2 : " + input);
                    }
                })
                .then(A.class, "target1")
                .commit();
        Shelly.playDomino("case01", "Haha");

        Shelly.createDomino("case03")
                .target(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Target action0");
                    }
                })
                .cachedThread()
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("cached thread1 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("cached thread2 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .newThread()
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("new Thread1 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("new Thread2 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .singleThread()
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("single Thread1 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("single Thread2 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .cachedThread()
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("cached thread3 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .then(new Action1() {
                    @Override
                    public void call(Object input) {
                        System.out.println("cached thread4 : " + Thread.currentThread().getName() + " " + input);
                    }
                })
                .commit();
        Shelly.playDomino("case03", "ABC");
    }
}
