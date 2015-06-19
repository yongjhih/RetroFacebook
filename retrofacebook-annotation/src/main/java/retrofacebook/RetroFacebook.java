/*
 * Copyright (C) 2015 8tory, Inc.
 * Copyright (C) 2012 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package retrofacebook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see <a href="https://developers.facebook.com/docs/reference/android/current/class/GraphRequest/">GraphRequest - Facebook Developers</a>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface RetroFacebook {

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.TYPE)
  public @interface Builder {
  }

  /**
   * Specifies that the annotated method is a validation method. The method should be a non-private
   * no-argument method in an RetroFacebook class. It will be called by the {@code build()} method of
   * the {@link Builder @RetroFacebook.Builder} implementation, immediately after constructing the new
   * object. It can throw an exception if the new object fails validation checks.
   */
  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface Validate {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface GET {
    String value();
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface POST {
    String value();
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface DELETE {
    String value();
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Path {
    String value() default "null";
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Query {
    String value() default "null";
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface QueryMap {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface QueryBundle {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Body {
  }
}
