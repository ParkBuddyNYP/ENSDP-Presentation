using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Http;
using Windows.ApplicationModel.Background;
using System.Diagnostics;
using System.Threading.Tasks;
using GrovePi;
using GrovePi.Sensors;
// The Background Application template is documented at http://go.microsoft.com/fwlink/?LinkID=533884&clcid=0x409

namespace AdminRFID
{
    public sealed class StartupTask : IBackgroundTask
    {

        private static SerialComms uartComms;
        private static string strRfidDetected = "";
        Pin buzzerPin = Pin.DigitalPin3;
        private void Sleep(int NoOfMs)
        {
            Task.Delay(NoOfMs).Wait();
        }

       
        private void soundWarning()
        {
            DeviceFactory.Build.GrovePi().AnalogWrite(buzzerPin, 60);
            Sleep(80);
            DeviceFactory.Build.GrovePi().AnalogWrite(buzzerPin, 120);
            Sleep(80);
            DeviceFactory.Build.GrovePi().AnalogWrite(buzzerPin, 60);
            Sleep(80);
            DeviceFactory.Build.GrovePi().AnalogWrite(buzzerPin, 120);
            Sleep(80);
            DeviceFactory.Build.GrovePi().AnalogWrite(buzzerPin, 0);
            Sleep(2000);
        }
        static void UartDataHandler(object sneder, SerialComms.UartEventArgs e)
        {
            strRfidDetected = e.data;
            Debug.WriteLine("Card detected : " + strRfidDetected);
        }
        private void StartUart()
        {
            uartComms = new SerialComms();
            uartComms.UartEvent += new SerialComms.UartEventDelegate(UartDataHandler);
        }
        public void Run(IBackgroundTaskInstance taskInstance)
        {
            StartUart();
            // 
            // TODO: Insert code to perform background work
            //
            // If you start any asynchronous methods here, prevent the task
            // from closing prematurely by using BackgroundTaskDeferral as
            // described in http://aka.ms/backgroundtaskdeferral
            //
            while (true)
            {
                Sleep(200);
                if (!strRfidDetected.Equals("070071FC8A00"))
                {
                   Debug.WriteLine("Invalid Card ");
                   soundWarning();
                    Sleep(5000);
                }
                else if (strRfidDetected.Equals("070071FC8A00"))
                {
                    Debug.WriteLine("Login Infromation has been send to Database");
                }
            }
        }
    }
}
