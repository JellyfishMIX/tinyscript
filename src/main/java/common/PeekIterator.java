package common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @author JellyfishMIX
 * @date 2020/4/30 6:51 下午
 */
public class PeekIterator<T> implements Iterator<T> {
    private Iterator<T> it;

    // 队列缓存
    private LinkedList<T> queueCache = new LinkedList<>();
    // 放回栈
    private LinkedList<T> stackPutBacks = new LinkedList<>();
    // 队列缓存大小
    private final static int CACHE_SIZE = 10;
    // 结束符
    private T endToken = null;

    public PeekIterator(Stream<T> stream) {
        it = stream.iterator();
    }

    public PeekIterator(Stream<T> stream, T endToken) {
        it = stream.iterator();
        this.endToken = endToken;
    }

    public PeekIterator(Iterator<T> it, T endToken) {
        this.it = it;
        this.endToken = endToken;
    }

    public T peek() {
        if (this.stackPutBacks.size() > 0) {
            return this.stackPutBacks.getFirst();
        }
        if (!it.hasNext()) {
            return endToken;
        }
        T val = this.next();
        this.putBack();
        return val;
    }

    // 缓存：A -> B -> C -> D
    // 放回：D -> C -> B -> A
    public void putBack() {
        if (this.queueCache.size() > 0) {
            this.stackPutBacks.push(this.queueCache.pollLast());
        }
    }

    @Override
    public boolean hasNext() {
        return endToken != null || this.stackPutBacks.size() > 0 || it.hasNext();
    }

    @Override
    public T next() {
        // 取流单元
        T val = null;
        if (this.stackPutBacks.size() > 0) {
            val = this.stackPutBacks.pop();
        } else {
            if (!it.hasNext()) {
                T tmp = endToken;
                endToken = null;
                return tmp;
            }
            val = it.next();
        }
        // 缓存队列大小限制
        while (queueCache.size() > CACHE_SIZE - 1) {
            queueCache.poll();
        }
        queueCache.add(val);
        // 返回当前流单元
        return val;
    }
}