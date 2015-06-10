/*
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
package auto.facebook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoFacebook {

  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.TYPE)
  public @interface Builder {
  }

  /**
   * Specifies that the annotated method is a validation method. The method should be a non-private
   * no-argument method in an AutoFacebook class. It will be called by the {@code build()} method of
   * the {@link Builder @AutoFacebook.Builder} implementation, immediately after constructing the new
   * object. It can throw an exception if the new object fails validation checks.
   */
  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.METHOD)
  public @interface Validate {
  }

  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.METHOD)
  public @interface Field {
  //public @interface Field extends com.bluelinelabs.logansquare.annotation.JsonField {
    /**
     * The name(s) of this field in JSON. Use an array if this could be represented by multiple names.
     * Note that using this field will override the enclosing JsonObject's fieldNamingPolicy.
     */
    String[] name() default {};

    /** The TypeConverter that will be used to parse/serialize this variable. */
    Class typeConverter() default void.class;
  }
}
