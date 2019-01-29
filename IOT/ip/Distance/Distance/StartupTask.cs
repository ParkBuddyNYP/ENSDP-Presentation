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

namespace Distance
{
    public sealed class StartupTask : IBackgroundTask
    {
        IUltrasonicRangerSensor sensor = DeviceFactory.Build.UltraSonicSensor(Pin.DigitalPin7);
        private int distance = 400;
       

        ILed ledRed = DeviceFactory.Build.Led(Pin.DigitalPin5);
        ILed ledGreen = DeviceFactory.Build.Led(Pin.DigitalPin6);

        private void Sleep(int NoOfMs)
        {
            Task.Delay(NoOfMs).Wait();

        }

        private async Task Distance()
        {

            
            int distanceRead = 400;
           
            distanceRead = sensor.MeasureInCentimeters();// best to find a async method

            Debug.WriteLine(" distance=" + distanceRead);
            if (distanceRead < 400 && distanceRead > 0)
                    distance = distanceRead;
                
               
            
        

        }
        private async Task startDistanceMonitoring()
        {


            while (true)
            {
                await Distance();
              
                if(distance>=100)
                {
                    ledGreen.ChangeState(SensorStatus.On);
                    ledRed.ChangeState(SensorStatus.Off);
                    await sendtoapi();
                    Sleep(5000);
                }
                else if(distance<50)
                {
                    ledRed.ChangeState(SensorStatus.On);
                    ledGreen.ChangeState(SensorStatus.Off);
                    await sendtoapi1();
                    Sleep(10000);
                }
                else
                {
                    ledGreen.ChangeState(SensorStatus.On);
                    ledRed.ChangeState(SensorStatus.Off);

                    ledGreen.ChangeState(SensorStatus.On);
                    ledRed.ChangeState(SensorStatus.Off);
                    await sendtoapi2();
                  
                    Sleep(5000);
                    




                }


                Sleep(5000);
            }
        }
        private async Task sendtoapi()
        {
            string myJson = "{ 'CarparkName': 'Hougang Mall','LotNumber':1,'NumberofLotsAvaliable':20,'TotalNumberofLots':20,'LotStatus':'Empty'}";

            using (var client = new HttpClient())
            {




                // var response = await client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/carparklots", content);
                var task = client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/CARPARKlots",
                new StringContent(myJson, Encoding.UTF8, "application/json"));


                // task.RunSynchronously();
                // task.Wait();
                while (!task.IsCompleted)
                {
                    Sleep(1000);
                } //  Result;
                // var responseString = response.Content.ReadAsStringAsync();
                Debug.WriteLine(task.Result);
            }
        }

        private async Task sendtoapi1()
        {
            string myJson = "{'CarparkName': 'Hougang Mall','LotNumber':1,'numberOfLotsAvailable':19,'TotalNumberofLots':20,'LotStatus':'Occupied'}";

            using (var client = new HttpClient())
            {




                // var response = await client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/carparklots", content);
                var task = client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/carparklots",
                new StringContent(myJson, Encoding.UTF8, "application/json"));


                // task.RunSynchronously();
                // task.Wait();
                while (!task.IsCompleted)
                {
                    Sleep(1000);
                } //  Result;
                // var responseString = response.Content.ReadAsStringAsync();
                Debug.WriteLine(task.Result);
            }
        }

        private async Task sendtoapi2()
        {
            string myJson = "{'CarparkName': 'Hougang Mall','LotNumber':1,'NumberofLotsAvaliable':20,'TotalNumberofLots':20,'LotStatus':'Maintenance'}";

            using (var client = new HttpClient())
            {




                // var response = await client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/carparklots", content);
                var task = client.PostAsync("https://parkbuddiesitp371.azurewebsites.net/api/carparklots",
                new StringContent(myJson, Encoding.UTF8, "application/json"));


                // task.RunSynchronously();
                // task.Wait();
                while (!task.IsCompleted)
                {
                    Sleep(1000);
                } //  Result;
                // var responseString = response.Content.ReadAsStringAsync();
                Debug.WriteLine(task.Result);
            }
        }
        
        public async void Run(IBackgroundTaskInstance taskInstance)
        {

            await startDistanceMonitoring();
            
            Debug.WriteLine(" distance=" + distance);
          
          
            
        }

    }




}


