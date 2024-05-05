using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Text;

namespace Lab9
{
    [XmlRoot(ElementName = "engine")]
    public class Engine
    {
        public double displacement { get; set; }
        public double horsePower { get; set; }
        [XmlAttribute]
        public string model { get; set; }

        public Engine() 
        { 
        
        }

        public Engine(double Displacement, double HorsePower, string Model)
        {
            displacement = Displacement;
            horsePower = HorsePower;
            model = Model;
        }

    }
}
