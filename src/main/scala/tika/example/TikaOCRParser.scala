package tika.example

import java.io.InputStream

import org.apache.tika.config.TikaConfig
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ocr.TesseractOCRConfig
import org.apache.tika.parser.pdf.PDFParserConfig
import org.apache.tika.parser.{AutoDetectParser, ParseContext, Parser}
import org.apache.tika.sax.BodyContentHandler

object TikaOCRParser {
  private val pdfConfig: PDFParserConfig = {
    val pdfConf = new PDFParserConfig()
    pdfConf.setOcrDPI(100) //scalastyle:ignore magic.number
    pdfConf.setDetectAngles(true)
    pdfConf.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.OCR_ONLY)

    pdfConf
  }

  private val tesseractOCRConfig: TesseractOCRConfig = {
    val tessConf = new TesseractOCRConfig()
    tessConf.setLanguage("eng")
    tessConf.setEnableImageProcessing(1)

    tessConf
  }

  private val parser = new AutoDetectParser(TikaConfig.getDefaultConfig)

  private val parseContext = {
    val parseCont = new ParseContext()
    parseCont.set(classOf[Parser], parser)
    parseCont.set(classOf[PDFParserConfig], pdfConfig)
    parseCont.set(classOf[TesseractOCRConfig], tesseractOCRConfig)
    parseCont
  }

  def parse(inputStream: InputStream, handler: BodyContentHandler, metadata: Metadata): Unit = parser.parse(inputStream, handler, metadata, parseContext)
}
