package net.augeas;

public final class SpanResult {
    private final String filename;
    private final int labelStart;
    private final int labelEnd;
    private final int valueStart;
    private final int valueEnd;
    private final int spanStart;
    private final int spanEnd;

    public SpanResult(String filename, int labelStart, int labelEnd, int valueStart, int valueEnd, int spanStart, int spanEnd) {
        this.filename = filename;
        this.labelStart = labelStart;
        this.labelEnd = labelEnd;
        this.valueStart = valueStart;
        this.valueEnd = valueEnd;
        this.spanStart = spanStart;
        this.spanEnd = spanEnd;
    }

    public String getFilename() {
        return filename;
    }

    public int getLabelStart() {
        return labelStart;
    }

    public int getLabelEnd() {
        return labelEnd;
    }

    public int getValueStart() {
        return valueStart;
    }

    public int getValueEnd() {
        return valueEnd;
    }

    public int getSpanStart() {
        return spanStart;
    }

    public int getSpanEnd() {
        return spanEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpanResult that = (SpanResult) o;

        if (labelEnd != that.labelEnd) return false;
        if (labelStart != that.labelStart) return false;
        if (spanEnd != that.spanEnd) return false;
        if (spanStart != that.spanStart) return false;
        if (valueEnd != that.valueEnd) return false;
        if (valueStart != that.valueStart) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + labelStart;
        result = 31 * result + labelEnd;
        result = 31 * result + valueStart;
        result = 31 * result + valueEnd;
        result = 31 * result + spanStart;
        result = 31 * result + spanEnd;
        return result;
    }

    @Override
    public String toString() {
        return "SpanResult{" +
                "filename='" + filename + '\'' +
                ", labelStart=" + labelStart +
                ", labelEnd=" + labelEnd +
                ", valueStart=" + valueStart +
                ", valueEnd=" + valueEnd +
                ", spanStart=" + spanStart +
                ", spanEnd=" + spanEnd +
                '}';
    }
}
