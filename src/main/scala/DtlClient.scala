import io.grpc.ManagedChannelBuilder
import org.tensorflow.framework.{TensorProto, TensorShapeProto}
import tensorflow.serving.Model.ModelSpec
import tensorflow.serving.Predict.PredictRequest
import tensorflow.serving.PredictionServiceGrpc

/**
  * Created by fzschornack on 14/11/2016.
  */
object DtlClient {

  def main(args: Array[String]): Unit = {
    val host = "192.168.56.101"
    val port = 9000
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build()
    val blockingStub = PredictionServiceGrpc.newBlockingStub(channel)

    val data = Array(36,54,49,60,53,70,0,21,53,66,69,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72,72)

    val tpDataBuilder = TensorProto.newBuilder()
    data.foreach( v => tpDataBuilder.addInt64Val(v.toLong))
    tpDataBuilder.setDtype(org.tensorflow.framework.DataType.DT_INT32)
    val tensorShapeBuilder = TensorShapeProto.newBuilder()
    tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1).build())
    tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(48).build())
    tpDataBuilder.setTensorShape(tensorShapeBuilder.build())
    val tensorProto = tpDataBuilder.build()

    val tpLabelBuilder = TensorProto.newBuilder()
    tpLabelBuilder.addInt64Val(1.toLong)
    tpLabelBuilder.setDtype(org.tensorflow.framework.DataType.DT_INT32)
    val tensorShapeLabelBuilder = TensorShapeProto.newBuilder()
    tensorShapeLabelBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1).build())
    tpLabelBuilder.setTensorShape(tensorShapeLabelBuilder.build())
    val tensorProtoLabel = tpLabelBuilder.build()


    val modelSpec = ModelSpec.newBuilder().setName("dtl").build()
    val requestBuilder = PredictRequest.newBuilder()
    requestBuilder.putInputs("input", tensorProto)
    requestBuilder.putInputs("input_label", tensorProtoLabel)
    requestBuilder.setModelSpec(modelSpec)
    val request = requestBuilder.build()

    val response = blockingStub.predict(request)
    println(response)

  }

}


