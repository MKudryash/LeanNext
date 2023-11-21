package com.example.leannext.utlis


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
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
import java.nio.file.Files
import java.nio.file.Paths

object ExportDataToCsv {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createXlFile(
        devindex: List<DevelopmentIndex>,
        nameDirections: List<Directions>,
        startDate: String,
        save: Boolean,
        context: Context
    ) {

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
        for (i in nameDirections.indices) {
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

        val file = checkFile(save, context, startDate)

        streamFolder(file,wb)

        if (!save) {
            SendOtherApp(context,file)
        }
        else Toast.makeText(context,"Успешно сохранено в загрузки!",Toast.LENGTH_LONG).show()

    }
    fun SendOtherApp(context: Context,file: File)
    {
        val uris = FileProvider.getUriForFile(context, "com.anni.shareimage.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uris)
        intent.type = "text/*"

        context.startActivity(Intent.createChooser(intent, "Share Via"))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkFile(save:Boolean, context:Context, startDate: String):File
    {
        val folder =
            if (save) Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            else context.cacheDir

        var fileName = "Отчет от $startDate.xlsx"

        var path = "" + folder + File.separator + fileName
        var flag = true
        var index = 1
        while (flag) {
            if (Files.exists(Paths.get(path))) {
                fileName = "Отчет от $startDate ($index).xlsx"
                path = "" + folder + File.separator + fileName
                index++
            } else {
                flag = false
            }
        }
        return File(folder, fileName)
    }
    fun streamFolder(file:File,wb:Workbook)
    {
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file.path)
            wb.write(outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            try {
                outputStream!!.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
