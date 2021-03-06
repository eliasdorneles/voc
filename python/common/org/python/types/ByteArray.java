package org.python.types;

import java.util.Arrays;

public class ByteArray extends org.python.types.Object {
    public byte[] value;

    /**
     * A utility method to update the internal value of this object.
     *
     * Used by __i*__ operations to do an in-place operation.
     * obj must be of type org.python.types.ByteArray
     */
    void setValue(org.python.Object obj) {
        this.value = ((org.python.types.ByteArray) obj).value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public ByteArray() {
        this.value = new byte[0];
    }

    public ByteArray(int length) {
        this.value = new byte[length];
    }

    public ByteArray(byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @org.python.Method(
            __doc__ = "bytearray(iterable_of_ints) -> bytearray" +
                    "bytearray(string, encoding[, errors]) -> bytearray\n" +
                    "bytearray(bytes_or_buffer) -> mutable copy of bytes_or_buffer\n" +
                    "bytearray(int) -> bytes array of size given by the parameter initialized with null bytes\n" +
                    "bytearray() -> empty bytes array\n" +
                    "\n" +
                    "Construct an mutable bytearray object from:\n" +
                    " - an iterable yielding integers in range(256)\n" +
                    " - a text string encoded using the specified encoding\n" +
                    " - a bytes or a buffer object\n" +
                    " - any object implementing the buffer API.\n" +
                    " - an integer\n",
            default_args = {"source", "encoding", "errors"}
    )
    public ByteArray(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        if (args[0] == null) {
            // bytearray()
            this.value = new byte[0];
        } else {
            if (args[1] == null) {
                if (args[0] instanceof org.python.types.Int) {
                    // bytearray(int)
                    this.value = new byte[(int) ((org.python.types.Int) args[0].__int__()).value];
                } else {

                    org.python.Object iterator = null;
                    try {
                        iterator = org.Python.iter(args[0]);
                    } catch (org.python.exceptions.TypeError e) {
                        // Not an iterator
                    }
                    if (iterator != null) {
                        // bytearray(iterable_of_ints)
                        java.util.List<Byte> generated = new java.util.ArrayList<Byte>();
                        try {
                            while (true) {
                                org.python.Object next = iterator.__next__();
                                if (next instanceof org.python.types.Int) {
                                    long value = ((org.python.types.Int) next.__int__()).value;
                                    if ((value < 0) || (value > 255)) {
                                        throw new org.python.exceptions.ValueError("byte must be in range(0, 256)");
                                    } else {
                                        generated.add(new Byte((byte) value));
                                    }
                                } else if (next instanceof org.python.types.Str) {
                                    // TODO: Can take ASCII single-character strings
                                    throw new org.python.exceptions.NotImplementedError("Builtin function 'bytearray' with strings not implemented");
                                }
                            }
                        } catch (org.python.exceptions.StopIteration si) {
                        }
                        byte[] primative_bytes = new byte[generated.size()];
                        for (int i = 0; i < primative_bytes.length; i++) {
                            primative_bytes[i] = generated.get(i);
                        }
                        this.value = primative_bytes;
                    } else {
                        // bytearray(bytes_or_buffer)
                        throw new org.python.exceptions.NotImplementedError("Builtin function 'bytearray' with bytes_or_buffer not implemented");
                    }
                }
            } else {
                // bytearray(string, args[1][, errors])
                if (args[2] == null) {
                    // bytearray(string, args[1])
                    throw new org.python.exceptions.NotImplementedError("Builtin function 'bytearray' not implemented");
                } else {
                    // bytearray(string, args[1], errors)
                    throw new org.python.exceptions.NotImplementedError("Builtin function 'bytearray' not implemented");
                }
            }
        }
    }

    // public ByteArray(org.python.types.Bool bool) {
    //     this.value = new byte [(bool.value == true) ? 1 : 0];
    // }

    // public org.python.Object __new__() {
    //     throw new org.python.exceptions.NotImplementedError("str.__new__() has not been implemented.");
    // }

    // public org.python.Object __init__() {
    //     throw new org.python.exceptions.NotImplementedError("str.__init__() has not been implemented.");
    // }

    @org.python.Method(
            __doc__ = "Return repr(self)."
    )
    public org.python.types.Str __repr__() {
        return this.__str__();
    }

    @org.python.Method(
            __doc__ = "Return str(self)."
    )
    public org.python.types.Str __str__() {
        StringBuilder sb = new StringBuilder();
        sb.append("bytearray(b'");
        for (int c : this.value) {
            if (c >= 32 && c < 128) {
                sb.append((char) c);
            } else {
                sb.append(String.format("\\x%02d", c));
            }
        }
        sb.append("')");
        return new org.python.types.Str(sb.toString());
    }

/*
    public org.python.types.Str __repr__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        if (kwargs != null && kwargs.size() != 0) {
            throw new org.python.exceptions.TypeError("bytearray.__repr__ doesn't take keyword arguments");
        } else if (args != null && args.size() != 0) {
            throw new org.python.exceptions.TypeError("Expected 0 arguments, got " + args.size());
        }

        try {
            return new org.python.types.Str("bytearray(b'" + new java.lang.String(this.value, "UTF-8") + "')");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new org.python.exceptions.UnicodeDecodeError();
        }
    }

    public org.python.types.Str __str__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        if (kwargs != null && kwargs.size() != 0) {
            throw new org.python.exceptions.TypeError("bytearray.__str__ doesn't take keyword arguments");
        } else if (args != null && args.size() != 0) {
            throw new org.python.exceptions.TypeError("Expected 0 arguments, got " + args.size());
        }

        try {
            return new org.python.types.Str(new java.lang.String(this.value, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new org.python.exceptions.UnicodeDecodeError();
        }
    }
*/

    @org.python.Method(
            __doc__ = "",
            args = {"other"}
    )
    public org.python.Object __and__(org.python.Object other) {
        throw new org.python.exceptions.TypeError("unsupported operand type(s) for &: '" + this.typeName() + "' and '" + other.typeName() + "'");
    }

    @org.python.Method(
            __doc__ = ""
    )
    public org.python.Object __pos__() {
        throw new org.python.exceptions.TypeError("bad operand type for unary +: '" + this.typeName() + "'");
    }

    @org.python.Method(
            __doc__ = ""
    )
    public org.python.Object __neg__() {
        throw new org.python.exceptions.TypeError("bad operand type for unary -: '" + this.typeName() + "'");
    }

    @org.python.Method(
            __doc__ = ""
    )
    public org.python.Object __invert__() {
        throw new org.python.exceptions.TypeError("bad operand type for unary ~: '" + this.typeName() + "'");
    }

    @org.python.Method(
            __doc__ = ""
    )
    public org.python.Object __bool__() {
        return new org.python.types.Bool(this.value.length > 0);
    }

    @org.python.Method(
            __doc__ = "Return self==value.",
            args = {"other"}
    )
    public org.python.Object __eq__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_value = ((org.python.types.Bytes) other).value;
            return new org.python.types.Bool(Arrays.equals(this.value, other_value));
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_value = ((org.python.types.ByteArray) other).value;
            return new org.python.types.Bool(Arrays.equals(this.value, other_value));
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(
            __doc__ = "Return self+value.",
            args = {"other"}
    )
    public org.python.Object __add__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_bytes = (byte[]) ((org.python.types.Bytes) other).value;
            byte[] new_bytes = new byte[this.value.length + other_bytes.length];
            System.arraycopy(this.value, 0, new_bytes, 0, this.value.length);
            System.arraycopy(other_bytes, 0, new_bytes, this.value.length, other_bytes.length);
            return new ByteArray(new_bytes);
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_bytes = (byte[]) ((org.python.types.ByteArray) other).value;
            if (other_bytes == null) {
                return this;
            }
            byte[] new_bytes = new byte[this.value.length + other_bytes.length];
            System.arraycopy(this.value, 0, new_bytes, 0, this.value.length);
            System.arraycopy(other_bytes, 0, new_bytes, this.value.length, other_bytes.length);
            return new ByteArray(new_bytes);
        }
        if (org.Python.VERSION < 0x03060200) {
            throw new org.python.exceptions.TypeError("can't concat " + this.typeName() + " to " + other.typeName());
        } else {
            throw new org.python.exceptions.TypeError("can't concat " + other.typeName() + " to " + this.typeName());
        }
    }

    @org.python.Method(
            __doc__ = "Implement self+=value.",
            args = {"other"}
    )
    public org.python.Object __iadd__(org.python.Object other) {
        try {
            return super.__iadd__(other);
        } catch (org.python.exceptions.TypeError ae) {
            if (org.Python.VERSION < 0x03060200) {
                throw new org.python.exceptions.TypeError("can't concat " + other.typeName() + " to " + this.typeName());
            } else {
                throw ae;
            }
        }
    }

    @org.python.Method(
            __doc__ = "Return self*value.n",
            args = {"other"}
    )
    public org.python.Object __mul__(org.python.Object other) {
        if (other instanceof org.python.types.Bool) {
            boolean other_bool = ((org.python.types.Bool) other).value;
            if (other_bool) {
                return this;
            } else {
                return new ByteArray();
            }
        }
        if (other instanceof org.python.types.Int) {
            int other_value = Math.max(0, (int) ((org.python.types.Int) other).value);
            int len = this.value.length;
            byte[] bytes = new byte[other_value * len];
            for (int i = 0; i < other_value; i++) {
                System.arraycopy(this.value, 0, bytes, i * len, len);
            }
            return new ByteArray(bytes);
        } else {
            throw new org.python.exceptions.TypeError("can't multiply sequence by non-int of type '" + other.typeName() + "'");
        }
    }

    @org.python.Method(
            __doc__ = "Implement self*=value.",
            args = {"other"}
    )
    public org.python.Object __imul__(org.python.Object other) {
        return this.__mul__(other);
    }

    @org.python.Method(
            __doc__ = "Return self[key].",
            args = {"index"}
    )
    public org.python.Object __getitem__(org.python.Object index) {
        if (index instanceof org.python.types.Slice) {
            org.python.types.Slice.ValidatedValue slice = ((org.python.types.Slice) index).validateValueTypes();
            byte[] sliced;

            // if (slice.start == null && slice.stop == null && slice.step == null) {
            int start;
            if (slice.start != null) {
                start = (int) slice.start.value;
            } else {
                start = 0;
            }

            int stop;
            if (slice.stop != null) {
                stop = (int) slice.stop.value;
            } else {
                stop = this.value.length;
            }
            if (stop > this.value.length) {
                stop = this.value.length;
            }

            int step;
            if (slice.step != null) {
                step = (int) slice.step.value;
            } else {
                step = 1;
            }

            if (step > 0) {
                if (start >= this.value.length || start > stop) {
                    return new ByteArray();
                }

                int len = (int) Math.ceil((float) (stop - start) / step);
                sliced = new byte[len];

                for (int i = 0, j = start; j < stop; i++, j += step) {
                    sliced[i] = this.value[j];
                }
            } else { // step < 0
                if (Math.abs(start) >= this.value.length || stop > start) {
                    return new ByteArray();
                }

                int len = (int) Math.ceil((float) (stop - start) / step);
                sliced = new byte[len];

                for (int i = 0, j = start; j > stop; i++, j += step) {
                    sliced[i] = this.value[j];
                }
            }
            return new ByteArray(sliced);
        } else if (index instanceof org.python.types.Bool || index instanceof org.python.types.Int) {
            int idx;
            if (index instanceof org.python.types.Bool) {
                boolean index_bool = ((org.python.types.Bool) index).value;
                if (index_bool) {
                    idx = 1;
                } else {
                    idx = 0;
                }
            } else {
                idx = (int) ((org.python.types.Int) index).value;
            }

            if (idx < 0) {
                if (-idx > this.value.length) {
                    throw new org.python.exceptions.IndexError("bytearray index out of range");
                } else {
                    idx = this.value.length + idx;
                    // return new Bytes(java.util.Arrays.copyOfRange(this.value, idx, idx));
                    return new org.python.types.Int(this.value[idx]);
                }
            } else {
                if (idx >= this.value.length) {
                    throw new org.python.exceptions.IndexError("bytearray index out of range");
                } else {
                    // return new Bytes(java.util.Arrays.copyOfRange(this.value, idx, idx));
                    return new org.python.types.Int(this.value[idx]);
                }
            }
        } else {
            if (org.Python.VERSION < 0x03050000) {
                throw new org.python.exceptions.TypeError("bytearray indices must be integers");
            } else {
                throw new org.python.exceptions.TypeError(
                        "bytearray indices must be integers or slices, not " + index.typeName()
                );
            }
        }
    }

    @org.python.Method(
            __doc__ = "Return self>=value.",
            args = {"other"}
    )
    public org.python.Object __ge__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_bytes = (byte[]) ((org.python.types.Bytes) other).value;
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length < other_bytes.length) {
                return new org.python.types.Bool(0);
            }
            return new org.python.types.Bool(1);
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_bytes = (byte[]) ((org.python.types.ByteArray) other).value;
            if (other_bytes == null) {
                return new org.python.types.Bool(1);
            }
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length < other_bytes.length) {
                return new org.python.types.Bool(0);
            }
            return new org.python.types.Bool(1);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(
            __doc__ = "Return self>value.",
            args = {"other"}
    )
    public org.python.Object __gt__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_bytes = (byte[]) ((org.python.types.Bytes) other).value;
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length <= other_bytes.length) {
                return new org.python.types.Bool(0);
            }
            return new org.python.types.Bool(1);
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_bytes = (byte[]) ((org.python.types.ByteArray) other).value;
            if (other_bytes == null) {
                other_bytes = new byte[0];
            }
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length <= other_bytes.length) {
                return new org.python.types.Bool(0);
            }
            return new org.python.types.Bool(1);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(
            __doc__ = "Return self<=value.",
            args = {"other"}
    )
    public org.python.Object __le__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_bytes = (byte[]) ((org.python.types.Bytes) other).value;
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length <= other_bytes.length) {
                return new org.python.types.Bool(1);
            }
            return new org.python.types.Bool(0);
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_bytes = (byte[]) ((org.python.types.ByteArray) other).value;
            if (other_bytes == null) {
                other_bytes = new byte[0];
            }
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length <= other_bytes.length) {
                return new org.python.types.Bool(1);
            }
            return new org.python.types.Bool(0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(
            __doc__ = "Return self<value.",
            args = {"other"}
    )
    public org.python.Object __lt__(org.python.Object other) {
        if (other instanceof org.python.types.Bytes) {
            byte[] other_bytes = (byte[]) ((org.python.types.Bytes) other).value;
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length < other_bytes.length) {
                return new org.python.types.Bool(1);
            }
            return new org.python.types.Bool(0);
        } else if (other instanceof org.python.types.ByteArray) {
            byte[] other_bytes = (byte[]) ((org.python.types.ByteArray) other).value;
            if (other_bytes == null) {
                return new org.python.types.Bool(0);
            }
            for (int i = 0; i < Math.min(this.value.length, other_bytes.length); i++) {
                if (this.value[i] < other_bytes[i]) {
                    return new org.python.types.Bool(1);
                }
                if (this.value[i] > other_bytes[i]) {
                    return new org.python.types.Bool(0);
                }
            }
            if (this.value.length < other_bytes.length) {
                return new org.python.types.Bool(1);
            }
            return new org.python.types.Bool(0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(
            __doc__ = "",
            args = {"other"}
    )
    public org.python.Object __mod__(org.python.Object other) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.TypeError(
                    "unsupported operand type(s) for %: 'bytearray' and '" + other.typeName() + "'"
            );
        } else {
            if (other instanceof org.python.types.List
                    || other instanceof org.python.types.Range
                    || other instanceof org.python.types.Dict) {
                int i, size;
                for (i = 0; i < this.value.length; i++) {
                    if (this.value[0] == 0) {
                        break;
                    }
                }
                if (org.Python.VERSION < 0x03050300) {
                    size = i;
                } else {
                    size = this.value.length;
                }
                byte[] bytes = new byte[size];
                System.arraycopy(this.value, 0, bytes, 0, size);
                return new ByteArray(bytes);
            }
            throw new org.python.exceptions.TypeError("not all arguments converted during bytes formatting");
        }
    }

    @org.python.Method(
            __doc__ = "",
            args = {"other"}
    )
    public org.python.Object __imod__(org.python.Object other) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.TypeError(
                    "unsupported operand type(s) for %=: 'bytearray' and '" + other.typeName() + "'"
            );
        } else {
            return this.__mod__(other);
        }
    }

    @org.python.Method(
        __doc__ = "Return true if contains the arg",
        args = {"slice"}
    )
    public org.python.Object __contains__(org.python.Object slice) {       
        return new Bytes(this.value).__contains__(slice);
    }

    @org.python.Method(
            __doc__ = "default object formatter"
    )
    public org.python.types.Str __format__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.__format__ has not been implemented.");
    }

    @org.python.Method(
            __doc__ = ""
    )
    public org.python.Object __getnewargs__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.__getnewargs__ has not been implemented.");
    }

    public boolean __setattr_null(java.lang.String name, org.python.Object value) {
        // Builtin types can't have attributes set on them.
        return false;
    }

    @org.python.Method(
            __doc__ = "Implement iter(self)."
    )
    public org.python.Object __iter__() {
        java.util.List<org.python.Object> listOfBytes = new java.util.ArrayList<org.python.Object>();
        for (byte b: this.value) {
            listOfBytes.add(new org.python.types.Int(b));
        }
        return new org.python.types.List(listOfBytes).__iter__();
    }

    @org.python.Method(
            __doc__ = "Return len(self)."
    )
    public org.python.types.Int __len__() {
        return new org.python.types.Int(this.value.length);
    }

    @org.python.Method(
            __doc__ = "Return state information for pickling."
    )
    public org.python.Object __reduce__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.__reduce__ has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "Return state information for pickling."
    )
    public org.python.Object __reduce_ex__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.__reduce_ex__ has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "Return self*value."
    )
    public org.python.Object __rmul__(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.__rmul__ has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.capitalize() -> copy of B\n\nReturn a copy of B with only its first character capitalized (ASCII)\nand the rest lower-cased."
    )
    public org.python.Object capitalize() {
        return new ByteArray(Bytes._capitalize(this.value));
    }

    @org.python.Method(
            __doc__ = "B.center(width[, fillchar]) -> copy of B\n\nReturn B centered in a string of length width.  Padding is\ndone using the specified fill character (default is a space)."
    )
    public org.python.Object center(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.center has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.count(sub[, start[, end]]) -> int\n\nReturn the number of non-overlapping occurrences of subsection sub in\nbytes B[start:end].  Optional arguments start and end are interpreted\nas in slice notation."
    )
    public org.python.Object count(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.count has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.decode(encoding='utf-8', errors='strict') -> str\n\nDecode B using the codec registered for encoding. Default encoding\nis 'utf-8'. errors may be given to set a different error\nhandling scheme.  Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError.  Other possible values are 'ignore' and 'replace'\nas well as any other name registered with codecs.register_error that is\nable to handle UnicodeDecodeErrors."
    )
    public org.python.Object decode(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        try {
            return new org.python.types.Str(new java.lang.String(this.value, "UTF-8"));
            // return new org.python.types.Str(new java.lang.String(this.value, encoding));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new org.python.exceptions.UnicodeDecodeError();
        }
    }

    @org.python.Method(
            __doc__ = "B.endswith(suffix[, start[, end]]) -> bool\n\nReturn True if B ends with the specified suffix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nsuffix can also be a tuple of bytes to try."
    )
    public org.python.Object endswith(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.endswith has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.expandtabs(tabsize=8) -> copy of B\n\nReturn a copy of B where all tab characters are expanded using spaces.\nIf tabsize is not given, a tab size of 8 characters is assumed."
    )
    public org.python.Object expandtabs(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.expandtabs has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.find(sub[, start[, end]]) -> int\n\nReturn the lowest index in B where subsection sub is found,\nsuch that sub is contained within B[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure."
    )
    public org.python.Object find(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.find has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "bytearray.fromhex(string) -> bytearray (static method)\n\nCreate a bytearray object from a string of hexadecimal numbers.\nSpaces between two numbers are accepted.\nExample: bytearray.fromhex('B9 01EF') -> bytearray(b'\\xb9\\x01\\xef')."
    )
    public org.python.Object fromhex(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.fromhex has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.index(sub[, start[, end]]) -> int\n\nLike B.find() but raise ValueError when the subsection is not found."
    )
    public org.python.Object index(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.index has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.isalnum() -> bool\n\nReturn True if all characters in B are alphanumeric\nand there is at least one character in B, False otherwise."
    )
    public org.python.Object isalnum(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.isalnum has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.isalpha() -> bool\n\nReturn True if all characters in B are alphabetic\nand there is at least one character in B, False otherwise."
    )
    public org.python.Object isalpha() {
        return new Bool(Bytes._isalpha(this.value));
    }

    @org.python.Method(
            __doc__ = "B.isdigit() -> bool\n\nReturn True if all characters in B are digits\nand there is at least one character in B, False otherwise."
    )
    public org.python.Object isdigit(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.isdigit has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.islower() -> bool\n\nReturn True if all cased characters in B are lowercase and there is\nat least one cased character in B, False otherwise."
    )
    public org.python.Object islower() {
        return new Bool(Bytes._islower(this.value));
    }

    @org.python.Method(
            __doc__ = "B.isspace() -> bool\n\nReturn True if all characters in B are whitespace\nand there is at least one character in B, False otherwise."
    )
    public org.python.Object isspace() {
        return new Bool(Bytes._isspace(this.value));
    }

    @org.python.Method(
            __doc__ = "B.istitle() -> bool\n\nReturn True if B is a titlecased string and there is at least one\ncharacter in B, i.e. uppercase characters may only follow uncased\ncharacters and lowercase characters only cased ones. Return False\notherwise."
    )
    public org.python.Object istitle(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.istitle has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.isupper() -> bool\n\nReturn True if all cased characters in B are uppercase and there is\nat least one cased character in B, False otherwise."
    )
    public org.python.Object isupper() {
        return new Bool(Bytes._isupper(this.value));
    }

    @org.python.Method(
            __doc__ = "B.join(iterable_of_bytes) -> bytearray\n\nConcatenate any number of bytes/bytearray objects, with B\nin between each pair, and return the result as a new bytearray."
    )
    public org.python.Object join(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.join has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.ljust(width[, fillchar]) -> copy of B\n\nReturn B left justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)."
    )
    public org.python.Object ljust(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.ljust has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.lower() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to lowercase."
    )
    public org.python.Object lower() {
        return new ByteArray(Bytes._lower(this.value));
    }

    @org.python.Method(
            __doc__ = "B.lstrip([bytes]) -> bytearray\n\nStrip leading bytes contained in the argument\nand return the result as a new bytearray.\nIf the argument is omitted, strip leading ASCII whitespace."
    )
    public org.python.Object lstrip(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.lstrip has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.maketrans(frm, to) -> translation table\n\nReturn a translation table (a bytes object of length 256) suitable\nfor use in the bytes or bytearray translate method where each byte\nin frm is mapped to the byte at the same position in to.\nThe bytes objects frm and to must be of the same length."
    )
    public org.python.Object maketrans(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.maketrans has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.partition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in B, and return the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, returns B and two empty bytearray objects."
    )
    public org.python.Object partition(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.partition has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.replace(old, new[, count]) -> bytearray\n\nReturn a copy of B with all occurrences of subsection\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced."
    )
    public org.python.Object replace(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.replace has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rfind(sub[, start[, end]]) -> int\n\nReturn the highest index in B where subsection sub is found,\nsuch that sub is contained within B[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure."
    )
    public org.python.Object rfind(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rfind has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rindex(sub[, start[, end]]) -> int\n\nLike B.rfind() but raise ValueError when the subsection is not found."
    )
    public org.python.Object rindex(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rindex has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rjust(width[, fillchar]) -> copy of B\n\nReturn B right justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)"
    )
    public org.python.Object rjust(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rjust has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rpartition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in B, starting at the end of B,\nand return the part before it, the separator itself, and the\npart after it.  If the separator is not found, returns two empty\nbytearray objects and B."
    )
    public org.python.Object rpartition(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rpartition has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rsplit(sep=None, maxsplit=-1) -> list of bytearrays\n\nReturn a list of the sections in B, using sep as the delimiter,\nstarting at the end of B and working to the front.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done."
    )
    public org.python.Object rsplit(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rsplit has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.rstrip([bytes]) -> bytearray\n\nStrip trailing bytes contained in the argument\nand return the result as a new bytearray.\nIf the argument is omitted, strip trailing ASCII whitespace."
    )
    public org.python.Object rstrip(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.rstrip has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.split(sep=None, maxsplit=-1) -> list of bytearrays\n\nReturn a list of the sections in B, using sep as the delimiter.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done."
    )
    public org.python.Object split(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.split has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.splitlines([keepends]) -> list of lines\n\nReturn a list of the lines in B, breaking at line boundaries.\nLine breaks are not included in the resulting list unless keepends\nis given and true."
    )
    public org.python.Object splitlines(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.splitlines has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.startswith(prefix[, start[, end]]) -> bool\n\nReturn True if B starts with the specified prefix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nprefix can also be a tuple of bytes to try."
    )
    public org.python.Object startswith(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.startswith has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.strip([bytes]) -> bytearray\n\nStrip leading and trailing bytes contained in the argument\nand return the result as a new bytearray.\nIf the argument is omitted, strip ASCII whitespace."
    )
    public org.python.Object strip(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.strip has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.swapcase() -> copy of B\n\nReturn a copy of B with uppercase ASCII characters converted\nto lowercase ASCII and vice versa."
    )
    public org.python.Object swapcase(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.swapcase has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.title() -> copy of B\n\nReturn a titlecased version of B, i.e. ASCII words start with uppercase\ncharacters, all remaining cased characters have lowercase."
    )
    public org.python.Object title(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.title has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.translate(table[, deletechars]) -> bytearray\n\nReturn a copy of B, where all characters occurring in the\noptional argument deletechars are removed, and the remaining\ncharacters have been mapped through the given translation\ntable, which must be a bytes object of length 256."
    )
    public org.python.Object translate(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.translate has not been implemented.");
    }

    @org.python.Method(
            __doc__ = "B.upper() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to uppercase."
    )
    public org.python.Object upper() {
        return new ByteArray(Bytes._upper(this.value));
    }

    @org.python.Method(
            __doc__ = "B.zfill(width) -> copy of B\n\nPad a numeric string B with zeros on the left, to fill a field\nof the specified width.  B is never truncated."
    )
    public org.python.Object zfill(java.util.List<org.python.Object> args, java.util.Map<java.lang.String, org.python.Object> kwargs, java.util.List<org.python.Object> default_args, java.util.Map<java.lang.String, org.python.Object> default_kwargs) {
        throw new org.python.exceptions.NotImplementedError("bytearray.zfill has not been implemented.");
    }
}
