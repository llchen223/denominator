package denominator.ultradns;

import static com.google.common.collect.Ordering.usingToString;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.jclouds.ultradns.ws.UltraDNSWSApi;
import org.jclouds.ultradns.ws.domain.ResourceRecordMetadata;
import org.jclouds.ultradns.ws.features.ResourceRecordApi;

import com.google.common.primitives.UnsignedInteger;

import denominator.ResourceRecordSetApi;
import denominator.model.ResourceRecordSet;

public final class UltraDNSResourceRecordSetApi implements denominator.ResourceRecordSetApi {
    static final class Factory implements denominator.ResourceRecordSetApi.Factory {

        private final UltraDNSWSApi api;

        @Inject
        Factory(UltraDNSWSApi api) {
            this.api = api;
        }

        @Override
        public ResourceRecordSetApi create(final String zoneName) {
            return new UltraDNSResourceRecordSetApi(api.getResourceRecordApiForZone(zoneName));
        }
    }

    private final ResourceRecordApi api;

    UltraDNSResourceRecordSetApi(ResourceRecordApi api) {
        this.api = api;
    }

    @Override
    public Iterator<ResourceRecordSet<?>> list() {
        Iterator<ResourceRecordMetadata> orderedRecords = api.list().toSortedList(usingToString()).iterator();
        return new GroupByRecordNameAndTypeIterator(orderedRecords);
    }

    @Override
    public void add(String name, String type, UnsignedInteger ttl, Map<String, Object> rdata) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void add(String name, String type, Map<String, Object> rdata) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void remove(String name, String type, Map<String, Object> rdata) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
