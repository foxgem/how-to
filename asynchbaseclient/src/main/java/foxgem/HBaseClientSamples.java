package foxgem;

import org.hbase.async.*;

public class HBaseClientSamples {

    HBaseClient client;

    public static void main(String[] args) throws Exception {
        HBaseClientSamples samples = new HBaseClientSamples();
        samples.compareAndSet("test", "row-1", "cf", "c3", new byte[] {1}, Bytes.fromLong(1));
        samples.hBaseClient().shutdown();
    }

    private void tableExists(String table) throws Exception {
        this.hBaseClient().ensureTableExists(table).addCallbacks(
                result -> {
                    System.out.println(result);
                    return result;
                },
                exception -> {
                    if (exception instanceof TableNotFoundException) {
                        System.out.print(table + " is not founded!");
                    }

                    return null;
                }
        ).join(5000);
    }

    private void get(String table, String key) throws Exception {
        this.hBaseClient().get(new GetRequest(table, key)).addCallbacks(
                result -> {
                    result.forEach(item -> {
                                System.out.println("Key: " + new String(item.key())
                                        + ", Value: " + new String(item.value())
                                        + ", Family: " + new String(item.family())
                                        + ", Qualifier: " + new String(item.qualifier())
                                );
                            }
                    );
                    return null;
                },
                exception -> {
                    return null;
                }
        ).join(5000);
    }

    private void put(String table, String key, String family, String qualifier, String value) throws Exception {
        this.hBaseClient().put(new PutRequest(table, key, family, qualifier, value));
    }

    private void scan(String table, int limit) throws Exception {
        Scanner scanner = this.hBaseClient().newScanner(table);
        scanner.nextRows(limit).addCallbacks(
                result -> {
                    result.forEach(row -> {
                        row.forEach(item -> {
                                    System.out.print("Key: " + new String(item.key())
                                            + ", Value: " + new String(item.value())
                                            + ", Family: " + new String(item.family())
                                            + ", Qualifier: " + new String(item.qualifier())
                                    );
                                }
                        );
                    });
                    return null;
                },
                exception -> {
                    return null;
                }
        ).join(5000);
        scanner.close();
    }

    private void delete(String table, String key) throws Exception {
        this.hBaseClient().delete(new DeleteRequest(table, key));
    }

    private void append(String table, String key, String family, String qualifier, String value) {
        this.hBaseClient().append(new AppendRequest(table, key, family, qualifier, value));
    }

    private void increment(String table, String key, String family, String qualifier, long amount) throws Exception {
        this.hBaseClient().atomicIncrement(new AtomicIncrementRequest(table, key, family, qualifier, amount));
    }

    private void compareAndSet(String table, String key, String family, String qualifier, byte[] oldValue, byte[] newValue) {
        this.hBaseClient().compareAndSet(new PutRequest(table.getBytes(), key.getBytes(),
                family.getBytes(), qualifier.getBytes(), newValue), oldValue);
    }

    private HBaseClient hBaseClient() {
        if (client == null) {
            Config config = new Config();
            config.overrideConfig("hbase.zookeeper.quorum", "localhost");
            client = new HBaseClient(config);
        }

        return client;
    }

}
