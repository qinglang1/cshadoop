import com.google.common.base.Strings;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.Sink.Status;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventHelper;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class csMySink extends AbstractSink {

    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = this.getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();
            if (event != null) {
                Map<String, String> headers = event.getHeaders();
                if (headers.size()!=0){


                }else {




                }
            } else {
                result = Status.BACKOFF;
            }

            transaction.commit();
        } catch (Exception var9) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, var9);
        } finally {
            transaction.close();
        }

        return result;
    }
}
