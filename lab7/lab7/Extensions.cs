using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Linq;

namespace Lab7
{
    public static class Extensions
    {
        public static FileInfo FindOldestInDirectory(this DirectoryInfo dir)
        {
            IEnumerable<DirectoryInfo> allDirs = dir.EnumerateDirectories();
            IEnumerable<FileInfo> allFilesInDirs = allDirs.Select(dirToExplore => FindOldestInDirectory(dirToExplore));
            IEnumerable<FileInfo> files = dir.EnumerateFiles();
            IEnumerable<FileInfo> allFiles = allFilesInDirs.Concat(files);
            IOrderedEnumerable<FileInfo> orderedByData = allFiles.OrderBy(x => x.CreationTimeUtc);

            return orderedByData.First();
        }

        public static string GetAttributes(this FileSystemInfo file)
        {
            FileAttributes attributes = file.Attributes;
            StringBuilder dosAttributes = new StringBuilder();
            dosAttributes.Append((attributes & FileAttributes.ReadOnly) != 0 ? 'R' : '-');
            dosAttributes.Append((attributes & FileAttributes.Archive) != 0 ? 'A' : '-');
            dosAttributes.Append((attributes & FileAttributes.Hidden) != 0 ? 'H' : '-');
            dosAttributes.Append((attributes & FileAttributes.System) != 0 ? 'S' : '-');
            string result = dosAttributes.ToString();
            return result;
        }
    }
}
