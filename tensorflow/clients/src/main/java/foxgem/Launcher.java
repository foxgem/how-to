package foxgem;

import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.PredictionServiceGrpc;

public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ManagedChannel channel = VertxChannelBuilder
                .forAddress(vertx, "localhost", 32768)
                .usePlaintext(true)
                .build();

        PredictionServiceGrpc.PredictionServiceBlockingStub stub = PredictionServiceGrpc.newBlockingStub(channel);

        logger.info("fire a request to hellTF Model ...");

        logger.info("result: {}", stub.predict(createRequest((float) 0.1)).getOutputsMap().get("y"));

        vertx.close();
    }

    private static TensorProto createTensorProto(float x) {
        TensorShapeProto.Dim dim = TensorShapeProto.Dim.newBuilder().setSize(1).build();

        TensorShapeProto shape = TensorShapeProto.newBuilder().addDim(dim).build();

        return TensorProto.newBuilder()
                .setDtype(DataType.DT_FLOAT)
                .setTensorShape(shape).addFloatVal(x).build();
    }

    private static Predict.PredictRequest createRequest(float x) {
        Model.ModelSpec modelSpec = Model.ModelSpec.newBuilder()
                .setName("inception")
                .setSignatureName("helloTF").build();

        return Predict.PredictRequest.newBuilder()
                .setModelSpec(modelSpec)
                .putInputs("x", createTensorProto(x)).build();
    }

}
