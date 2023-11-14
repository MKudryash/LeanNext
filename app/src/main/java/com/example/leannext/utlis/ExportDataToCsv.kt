package com.example.leannext.utlis


import android.os.Environment
import android.util.Log
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.round


class ExportDataToCsv () {

    fun createXlFile(devindex:List<DevelopmentIndex>,nameDirections: List<Directions>,startDate:String) {


        // File filePath = new File(Environment.getExternalStorageDirectory() + "/Demo.xls");
        val wb: Workbook = HSSFWorkbook()
        var cell: Cell? = null
        var sheet: Sheet? = null
        sheet = wb.createSheet("Result")
        //Now column and row
        val row: Row = sheet.createRow(0)
        cell = row.createCell(0)
        cell.setCellValue("Наименование критерия")
        cell = row.createCell(1)
        cell.setCellValue("Оценка")
        for(i in nameDirections.indices){
            val row1: Row = sheet.createRow(i + 1)
            cell = row1.createCell(0)
            cell.setCellValue(nameDirections[i].title)
            sheet.setColumnWidth(0, 20 * 200)
            var index = 0.0

            devindex.forEach {
                if (it.idDirection == nameDirections[i].id) index = it.mark
            }
            cell = row1.createCell(1)
            cell.setCellValue(String.format("%.1f", index))
        }

        val folder: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // Storing the data in file with name as geeksData.txt


        val fileName = "Отчет от $startDate.xls"
        val path =  ""+folder+File.separator + fileName
        val file =
            File(folder, fileName)
        /*    if (!file.exists()) {
                file.mkdirs()
            }*/
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(path)
            wb.write(outputStream)
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Log.d("TEST", "Excel Created in $path")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("TEST", e.message.toString())
            try {
                outputStream!!.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        

    }

}
