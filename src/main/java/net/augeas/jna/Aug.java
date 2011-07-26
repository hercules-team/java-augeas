/*
 * Aug.java
 *
 * Copyright (C) 2009 Red Hat Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 *
 * Author: Bryan Kearney <bkearney@redhat.com>
 */
package net.augeas.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * Mapping of the c code to JNA
 */
public interface Aug extends Library {

    Aug INSTANCE = (Aug) Native.loadLibrary("augeas", Aug.class);

    int aug_close(AugPointer aug);

    int aug_defnode(AugPointer aug, String name, String expr, String value, IntByReference created);

    int aug_defvar(AugPointer aug, String name, String expr);

    int aug_error(AugPointer aug);

    String aug_error_details(AugPointer aug);

    String aug_error_message(AugPointer aug);

    String aug_error_minor_message(AugPointer aug);

    int aug_get(AugPointer aug, String path, StringArray value);

    AugPointer aug_init(String root, String loadpath, int flags);

    int aug_insert(AugPointer aug, String path, String label, int before);

    int aug_load(AugPointer aug);

    int aug_match(AugPointer aug, String path, PointerByReference matches);

    int aug_mv(AugPointer aug, String src, String dest);

    int aug_rm(AugPointer aug, String path);

    int aug_save(AugPointer aug);

    int aug_set(AugPointer aug, String path, String value);

    int aug_setm(AugPointer aug, String base, String sub, String value);

    int aug_span(AugPointer aug, String path, PointerByReference filename,
                 IntByReference labelStart, IntByReference labelEnd,
                 IntByReference valueStart, IntByReference valueEnd,
                 IntByReference spanStart, IntByReference spanEnd);
}