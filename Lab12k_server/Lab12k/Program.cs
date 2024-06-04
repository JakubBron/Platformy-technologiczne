using System;
using System.Threading;
using System.Threading.Tasks;

namespace Lab12k
{
    class Program
    {
        public static async Task Main()
        {
            var cancellationTokenSource = new CancellationTokenSource();
            //Console.CancelKeyPress += (_, _) => cancellationTokenSource.Cancel();
            Console.CancelKeyPress += new ConsoleCancelEventHandler(OnCancelKeyPress);
            void OnCancelKeyPress(object sender, ConsoleCancelEventArgs e)
            {
                //e.Cancel = true; // Prevent the process from terminating immediately
                cancellationTokenSource.Cancel();
            }
            var cancellationToken = cancellationTokenSource.Token;

            var server = new Server();
            await server.Start(cancellationToken);
        }
    }
}
