using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;


namespace Lab7
{
    class Program
    {
        static void Main(string[] args)
        {
            if(args.Length < 1)
            {
                Console.WriteLine("Usage: program.exe path/to/directory");
                return;
            }
            DirectoryInfo dir = new DirectoryInfo(Path.GetFullPath(args[0]));
            printDirectioriesFromLocation(dir, "");
            

            Console.WriteLine("############ next task ###########");
            serializeAndDeserialize(dir);
        }

        private static void serializeAndDeserialize(DirectoryInfo dir)
        {
            SortedDictionary<string, long> collection = makeCollection(dir);

            try
            {
                MemoryStream ms = new MemoryStream();
                BinaryFormatter f = new BinaryFormatter();
                f.Serialize(ms, collection);
                Console.WriteLine("Serialized!\n");

                Console.WriteLine("Deserializing...\n");
                ms.Position = 0;
                SortedDictionary<string, long> deserialized = (SortedDictionary<string, long>)f.Deserialize(ms);
                Console.WriteLine("Dictionary contains: ");
                foreach (KeyValuePair<string, long> p in deserialized)
                {
                    Console.WriteLine($"{p.Key} -> {p.Value}");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("An error occurred while serializing/deserializing. " + e.Message);
            }
            return;
        }

        private static SortedDictionary<string, long> makeCollection(DirectoryInfo dir)
        {
            IEnumerable<(string, long)> fileNameLength = dir.EnumerateFiles().Select(file => (file.Name, file.Length));
            IEnumerable<(string, long)> dirsNameSize = dir.EnumerateDirectories()
                .Select(dir => (dir.Name, dir.EnumerateDirectories().Count() + dir.EnumerateFiles().LongCount()));
            // for all subdirs&files directly in 'dir' we need to count all its subdirs & files (distance from 'dir' >= 2)
            
            IEnumerable<(string , long )> collection = fileNameLength.Concat(dirsNameSize);
            //collection = collection.OrderBy(x => x.Item1.Length).ThenBy(x => x.Item1);
            
             collection.ToDictionary(x => x.Item1, x => x.Item2);
            
             SortedDictionary<string, long> result = new SortedDictionary<string, long>(new Comparator());
             foreach ((string, long) p in collection)
             {
                 result[p.Item1] = p.Item2;
                 Console.WriteLine($"{p.Item1}, {p.Item2}");
             }
             
             return result;
        }

        private static void printDirectioriesFromLocation(DirectoryInfo dir, string tabulators)
        {
            var files = dir.EnumerateFiles().ToArray();
            var subdirs = dir.EnumerateDirectories().ToArray();
            const string offset = "    ";

            Console.WriteLine($"{dir.Name}/ with {files.Length} files & {subdirs.Length} subdir(s): ");
            foreach(var file in files) {
                Console.WriteLine($"{tabulators}{file.Name}\t{file.Length} B \t {file.GetAttributes()}");

            }
            foreach (var subdir in subdirs)
            {
                printDirectioriesFromLocation(subdir, tabulators + offset);
            }
            Console.WriteLine();
            return;
        }
    }
}
