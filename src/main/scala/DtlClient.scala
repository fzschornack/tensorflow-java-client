import io.grpc.ManagedChannelBuilder
import org.tensorflow.framework.TensorProto
import tensorflow.serving.Model.ModelSpec
import tensorflow.serving.Predict.PredictRequest
import tensorflow.serving.PredictionServiceGrpc

/**
  * Created by fzschornack on 14/11/2016.
  */
object DtlClient {

  def main(args: Array[String]): Unit = {
    val host = "host"
    val port = 9000
    val channel = ManagedChannelBuilder.forAddress(host, port).build()
    val blockingStub = PredictionServiceGrpc.newBlockingStub(channel)

    val data = Array(36,54,49,60,53,70,0,21,53,66,69,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72)
    val tensorProtoBuilder = TensorProto.newBuilder()
    data.foreach( v => tensorProtoBuilder.addInt64Val(v.toLong))
    val tensorProto = tensorProtoBuilder.build()

    val modelSpec = ModelSpec.newBuilder().setName("dtl").build()
    val request = PredictRequest.newBuilder().putInputs("input", tensorProto)
      .setModelSpec(modelSpec)
      .build()

    val response = blockingStub.predict(request)
    println(response)

  }

}


