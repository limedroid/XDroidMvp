package cn.jesse.nativelogger.util

import android.text.TextUtils
import cn.jesse.nativelogger.NLogger
import java.io.*
import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 压缩工具
 *
 * @author Jesse
 */
object ZipUtil {
    val SUFFIX_ZIP = ".zip"
    private val BUFF_SIZE = 1024 * 1024
    private val SUFFIX_LOCK = ".lck"

    /**
     * get suitable files from path depend on pack num ,clear redundant files
     *
     * @param path source files path
     * @param expiredPeriod expired file period
     */
    fun getSuitableFilesWithClear(path: String, expiredPeriod: Int): Collection<File> {
        val files = ArrayList<File>()
        val file = File(path)
        val subFile = file.listFiles()
        if (subFile != null && !subFile.isEmpty()) {
            for (item in subFile) {
                if (item.isDirectory) {
                    continue
                }

                val expired = expiredPeriod * 24 * 60 * 60 * 1000L
                if ((System.currentTimeMillis() - item.lastModified() > expired) && !item.delete()) {
                    NLogger.e("can not delete expired file " + item.name)
                }

                if (item.name.endsWith(SUFFIX_LOCK) || item.name.endsWith(SUFFIX_ZIP)) {
                    continue
                }

                files.add(item)

            }
        }
        return files
    }

    /**
     * zip files
     *
     * @param resFileList zip from files
     * @param zipFile zip to file
     * @param comment comment of target file
     */
    @Throws(IOException::class)
    fun zipFiles(resFileList: Collection<File>?, zipFile: File, comment: String): Boolean {
        if (null == resFileList || resFileList.isEmpty()) {
            return false
        }

        val zipOutputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile), BUFF_SIZE))
        for (resFile in resFileList) {
            zipFile(resFile, zipOutputStream, "")
        }

        if (!TextUtils.isEmpty(comment)) {
            zipOutputStream.setComment(comment)
        }

        CloseUtil.close(zipOutputStream)
        return true
    }

    /**
     * zip file
     *
     * @param resFile zip from file
     * @param zipOut zip to file
     * @param rootPath target file path
     */
    @Throws(IOException::class)
    fun zipFile(resFile: File, zipOut: ZipOutputStream, rootPath: String) {

        var separator = ""
        if (rootPath.isNotEmpty()) {
            separator = File.separator
        }

        var filePath = rootPath + separator + resFile.name
        filePath = String(filePath.toByteArray(), Charset.forName("GB2312"))

        if (resFile.isDirectory) {
            val fileList = resFile.listFiles()

            for (file in fileList!!) {
                zipFile(file, zipOut, filePath)
            }
            return
        }

        val buffer = ByteArray(BUFF_SIZE)
        val bis = BufferedInputStream(FileInputStream(resFile), BUFF_SIZE)
        zipOut.putNextEntry(ZipEntry(filePath))
        var realLength = bis.read(buffer)

        while (realLength != -1) {
            zipOut.write(buffer, 0, realLength)
            realLength = bis.read(buffer)
        }

        CloseUtil.close(bis)
        zipOut.flush()
        zipOut.closeEntry()
    }
}
