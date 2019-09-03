import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @author wangqinghui
 * @version v1.0
 * @date 2019/7/24
 */
public class Demo {

    public void pool(){
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        ByteBuf parent = alloc.directBuffer();
        alloc.buffer();
        parent.writeBytes(DEMO_BYTES);
    }
}
