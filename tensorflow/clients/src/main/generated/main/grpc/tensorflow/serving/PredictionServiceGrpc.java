package tensorflow.serving;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * open source marker; do not remove
 * PredictionService provides access to machine-learned models loaded by
 * model_servers.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.3.0)",
    comments = "Source: tensorflow_serving/apis/prediction_service.proto")
public final class PredictionServiceGrpc {

  private PredictionServiceGrpc() {}

  private static <T> io.grpc.stub.StreamObserver<T> toObserver(final io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> handler) {
    return new io.grpc.stub.StreamObserver<T>() {
      private volatile boolean resolved = false;
      @Override
      public void onNext(T value) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture(value));
        }
      }

      @Override
      public void onError(Throwable t) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.failedFuture(t));
        }
      }

      @Override
      public void onCompleted() {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture());
        }
      }
    };
  }

  public static final String SERVICE_NAME = "tensorflow.serving.PredictionService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<tensorflow.serving.Classification.ClassificationRequest,
      tensorflow.serving.Classification.ClassificationResponse> METHOD_CLASSIFY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "tensorflow.serving.PredictionService", "Classify"),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.Classification.ClassificationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.Classification.ClassificationResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<tensorflow.serving.RegressionOuterClass.RegressionRequest,
      tensorflow.serving.RegressionOuterClass.RegressionResponse> METHOD_REGRESS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "tensorflow.serving.PredictionService", "Regress"),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.RegressionOuterClass.RegressionRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.RegressionOuterClass.RegressionResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<tensorflow.serving.Predict.PredictRequest,
      tensorflow.serving.Predict.PredictResponse> METHOD_PREDICT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "tensorflow.serving.PredictionService", "Predict"),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.Predict.PredictRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.Predict.PredictResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<tensorflow.serving.GetModelMetadata.GetModelMetadataRequest,
      tensorflow.serving.GetModelMetadata.GetModelMetadataResponse> METHOD_GET_MODEL_METADATA =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "tensorflow.serving.PredictionService", "GetModelMetadata"),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(tensorflow.serving.GetModelMetadata.GetModelMetadataResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PredictionServiceStub newStub(io.grpc.Channel channel) {
    return new PredictionServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PredictionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PredictionServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static PredictionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PredictionServiceFutureStub(channel);
  }

  /**
   * Creates a new vertx stub that supports all call types for the service
   */
  public static PredictionServiceVertxStub newVertxStub(io.grpc.Channel channel) {
    return new PredictionServiceVertxStub(channel);
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static abstract class PredictionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public void classify(tensorflow.serving.Classification.ClassificationRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.Classification.ClassificationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CLASSIFY, responseObserver);
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public void regress(tensorflow.serving.RegressionOuterClass.RegressionRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.RegressionOuterClass.RegressionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGRESS, responseObserver);
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public void predict(tensorflow.serving.Predict.PredictRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PREDICT, responseObserver);
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public void getModelMetadata(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_MODEL_METADATA, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CLASSIFY,
            asyncUnaryCall(
              new MethodHandlers<
                tensorflow.serving.Classification.ClassificationRequest,
                tensorflow.serving.Classification.ClassificationResponse>(
                  this, METHODID_CLASSIFY)))
          .addMethod(
            METHOD_REGRESS,
            asyncUnaryCall(
              new MethodHandlers<
                tensorflow.serving.RegressionOuterClass.RegressionRequest,
                tensorflow.serving.RegressionOuterClass.RegressionResponse>(
                  this, METHODID_REGRESS)))
          .addMethod(
            METHOD_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                tensorflow.serving.Predict.PredictRequest,
                tensorflow.serving.Predict.PredictResponse>(
                  this, METHODID_PREDICT)))
          .addMethod(
            METHOD_GET_MODEL_METADATA,
            asyncUnaryCall(
              new MethodHandlers<
                tensorflow.serving.GetModelMetadata.GetModelMetadataRequest,
                tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>(
                  this, METHODID_GET_MODEL_METADATA)))
          .build();
    }
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static final class PredictionServiceStub extends io.grpc.stub.AbstractStub<PredictionServiceStub> {
    private PredictionServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public void classify(tensorflow.serving.Classification.ClassificationRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.Classification.ClassificationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CLASSIFY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public void regress(tensorflow.serving.RegressionOuterClass.RegressionRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.RegressionOuterClass.RegressionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public void predict(tensorflow.serving.Predict.PredictRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PREDICT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public void getModelMetadata(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request,
        io.grpc.stub.StreamObserver<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_MODEL_METADATA, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static final class PredictionServiceBlockingStub extends io.grpc.stub.AbstractStub<PredictionServiceBlockingStub> {
    private PredictionServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public tensorflow.serving.Classification.ClassificationResponse classify(tensorflow.serving.Classification.ClassificationRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CLASSIFY, getCallOptions(), request);
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public tensorflow.serving.RegressionOuterClass.RegressionResponse regress(tensorflow.serving.RegressionOuterClass.RegressionRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public tensorflow.serving.Predict.PredictResponse predict(tensorflow.serving.Predict.PredictRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PREDICT, getCallOptions(), request);
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public tensorflow.serving.GetModelMetadata.GetModelMetadataResponse getModelMetadata(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_MODEL_METADATA, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static final class PredictionServiceFutureStub extends io.grpc.stub.AbstractStub<PredictionServiceFutureStub> {
    private PredictionServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tensorflow.serving.Classification.ClassificationResponse> classify(
        tensorflow.serving.Classification.ClassificationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CLASSIFY, getCallOptions()), request);
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tensorflow.serving.RegressionOuterClass.RegressionResponse> regress(
        tensorflow.serving.RegressionOuterClass.RegressionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tensorflow.serving.Predict.PredictResponse> predict(
        tensorflow.serving.Predict.PredictRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PREDICT, getCallOptions()), request);
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse> getModelMetadata(
        tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_MODEL_METADATA, getCallOptions()), request);
    }
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static abstract class PredictionServiceVertxImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public void classify(tensorflow.serving.Classification.ClassificationRequest request,
        io.vertx.core.Future<tensorflow.serving.Classification.ClassificationResponse> response) {
      asyncUnimplementedUnaryCall(METHOD_CLASSIFY, PredictionServiceGrpc.toObserver(response.completer()));
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public void regress(tensorflow.serving.RegressionOuterClass.RegressionRequest request,
        io.vertx.core.Future<tensorflow.serving.RegressionOuterClass.RegressionResponse> response) {
      asyncUnimplementedUnaryCall(METHOD_REGRESS, PredictionServiceGrpc.toObserver(response.completer()));
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public void predict(tensorflow.serving.Predict.PredictRequest request,
        io.vertx.core.Future<tensorflow.serving.Predict.PredictResponse> response) {
      asyncUnimplementedUnaryCall(METHOD_PREDICT, PredictionServiceGrpc.toObserver(response.completer()));
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public void getModelMetadata(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request,
        io.vertx.core.Future<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse> response) {
      asyncUnimplementedUnaryCall(METHOD_GET_MODEL_METADATA, PredictionServiceGrpc.toObserver(response.completer()));
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CLASSIFY,
            asyncUnaryCall(
              new VertxMethodHandlers<
                tensorflow.serving.Classification.ClassificationRequest,
                tensorflow.serving.Classification.ClassificationResponse>(
                  this, METHODID_CLASSIFY)))
          .addMethod(
            METHOD_REGRESS,
            asyncUnaryCall(
              new VertxMethodHandlers<
                tensorflow.serving.RegressionOuterClass.RegressionRequest,
                tensorflow.serving.RegressionOuterClass.RegressionResponse>(
                  this, METHODID_REGRESS)))
          .addMethod(
            METHOD_PREDICT,
            asyncUnaryCall(
              new VertxMethodHandlers<
                tensorflow.serving.Predict.PredictRequest,
                tensorflow.serving.Predict.PredictResponse>(
                  this, METHODID_PREDICT)))
          .addMethod(
            METHOD_GET_MODEL_METADATA,
            asyncUnaryCall(
              new VertxMethodHandlers<
                tensorflow.serving.GetModelMetadata.GetModelMetadataRequest,
                tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>(
                  this, METHODID_GET_MODEL_METADATA)))
          .build();
    }
  }

  /**
   * <pre>
   * open source marker; do not remove
   * PredictionService provides access to machine-learned models loaded by
   * model_servers.
   * </pre>
   */
  public static final class PredictionServiceVertxStub extends io.grpc.stub.AbstractStub<PredictionServiceVertxStub> {
    private PredictionServiceVertxStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceVertxStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceVertxStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceVertxStub(channel, callOptions);
    }

    /**
     * <pre>
     * Classify.
     * </pre>
     */
    public void classify(tensorflow.serving.Classification.ClassificationRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<tensorflow.serving.Classification.ClassificationResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CLASSIFY, getCallOptions()), request, PredictionServiceGrpc.toObserver(response));
    }

    /**
     * <pre>
     * Regress.
     * </pre>
     */
    public void regress(tensorflow.serving.RegressionOuterClass.RegressionRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<tensorflow.serving.RegressionOuterClass.RegressionResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGRESS, getCallOptions()), request, PredictionServiceGrpc.toObserver(response));
    }

    /**
     * <pre>
     * Predict -- provides access to loaded TensorFlow model.
     * </pre>
     */
    public void predict(tensorflow.serving.Predict.PredictRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<tensorflow.serving.Predict.PredictResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PREDICT, getCallOptions()), request, PredictionServiceGrpc.toObserver(response));
    }

    /**
     * <pre>
     * GetModelMetadata - provides access to metadata for loaded models.
     * </pre>
     */
    public void getModelMetadata(tensorflow.serving.GetModelMetadata.GetModelMetadataRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_MODEL_METADATA, getCallOptions()), request, PredictionServiceGrpc.toObserver(response));
    }
  }

  private static final int METHODID_CLASSIFY = 0;
  private static final int METHODID_REGRESS = 1;
  private static final int METHODID_PREDICT = 2;
  private static final int METHODID_GET_MODEL_METADATA = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PredictionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PredictionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CLASSIFY:
          serviceImpl.classify((tensorflow.serving.Classification.ClassificationRequest) request,
              (io.grpc.stub.StreamObserver<tensorflow.serving.Classification.ClassificationResponse>) responseObserver);
          break;
        case METHODID_REGRESS:
          serviceImpl.regress((tensorflow.serving.RegressionOuterClass.RegressionRequest) request,
              (io.grpc.stub.StreamObserver<tensorflow.serving.RegressionOuterClass.RegressionResponse>) responseObserver);
          break;
        case METHODID_PREDICT:
          serviceImpl.predict((tensorflow.serving.Predict.PredictRequest) request,
              (io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse>) responseObserver);
          break;
        case METHODID_GET_MODEL_METADATA:
          serviceImpl.getModelMetadata((tensorflow.serving.GetModelMetadata.GetModelMetadataRequest) request,
              (io.grpc.stub.StreamObserver<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class VertxMethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PredictionServiceVertxImplBase serviceImpl;
    private final int methodId;

    VertxMethodHandlers(PredictionServiceVertxImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CLASSIFY:
          serviceImpl.classify((tensorflow.serving.Classification.ClassificationRequest) request,
              (io.vertx.core.Future<tensorflow.serving.Classification.ClassificationResponse>) io.vertx.core.Future.<tensorflow.serving.Classification.ClassificationResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<tensorflow.serving.Classification.ClassificationResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_REGRESS:
          serviceImpl.regress((tensorflow.serving.RegressionOuterClass.RegressionRequest) request,
              (io.vertx.core.Future<tensorflow.serving.RegressionOuterClass.RegressionResponse>) io.vertx.core.Future.<tensorflow.serving.RegressionOuterClass.RegressionResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<tensorflow.serving.RegressionOuterClass.RegressionResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_PREDICT:
          serviceImpl.predict((tensorflow.serving.Predict.PredictRequest) request,
              (io.vertx.core.Future<tensorflow.serving.Predict.PredictResponse>) io.vertx.core.Future.<tensorflow.serving.Predict.PredictResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_GET_MODEL_METADATA:
          serviceImpl.getModelMetadata((tensorflow.serving.GetModelMetadata.GetModelMetadataRequest) request,
              (io.vertx.core.Future<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>) io.vertx.core.Future.<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<tensorflow.serving.GetModelMetadata.GetModelMetadataResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class PredictionServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return tensorflow.serving.PredictionServiceOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PredictionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PredictionServiceDescriptorSupplier())
              .addMethod(METHOD_CLASSIFY)
              .addMethod(METHOD_REGRESS)
              .addMethod(METHOD_PREDICT)
              .addMethod(METHOD_GET_MODEL_METADATA)
              .build();
        }
      }
    }
    return result;
  }
}
