package tika.example

import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler

import scala.util.{Failure, Success, Using}

object TikaOCRApplication extends App {
  val input = getClass.getResourceAsStream("/ExampleOCR.jpg")

  val outputStream = new ByteArrayOutputStream()

  val attemptedOCR = Using(input) { inputStream =>
    TikaOCRParser.parse(inputStream, new BodyContentHandler(outputStream), new Metadata())
  }.map { _ =>
    new String(outputStream.toByteArray, Charset.defaultCharset())
  }

  attemptedOCR match {
    case Success(value) => println(s"OCR result was: $value")
    case Failure(exception) => println(s"OCR has failed, exception message was: ${exception.getMessage}")
  }

}
