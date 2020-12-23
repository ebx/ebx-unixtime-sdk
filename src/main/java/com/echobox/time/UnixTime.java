/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echobox.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple utility class to get the current unix time in a more concise fashion!
 *
 * @author MarcF
 */
public class UnixTime {

  /**
   * Now long.
   *
   * @return long long
   */
  public static long now() {
    return (long) (System.currentTimeMillis() / 1000L);
  }

  /**
   * Formats the provided Unix time in UTC for debugging purposes
   *
   * @param unixTime the unix time
   * @return string string
   */
  public static String formatUnixTimeUTC(Long unixTime) {
    if (unixTime == null) {
      return "NULL";
    }

    Date date = new Date(unixTime * 1000L); // *1000 is to convert seconds to milliseconds
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(date);
  }

  /**
   * Formats the provided Unix time in UTC for debugging purposes
   *
   * @param unixTime the unix time
   * @return string string
   */
  public static String formatUnixTimeUTC(Integer unixTime) {
    if (unixTime == null) {
      return "NULL";
    }
    return formatUnixTimeUTC((long) unixTime);
  }
}
