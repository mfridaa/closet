//
//  ToiletMKPointAnnotation.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 22..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import MapKit

class ToiletMKPointAnnotation : MKPointAnnotation {
    var id:Int16?
    func refreshStatus(){
        if let id = id{
            if let url = URL(string : URLStorage.getToilet(forToiletId: Int(id)))  {
                URLSession.shared.dataTask(with: url) { data, response, err in
                    if let data = data {
                        do{
                            let newToilet = try JSONDecoder().decode(BasicToilet.self, from: data)
                            DispatchQueue.main.async {
                                self.subtitle = newToilet.status
                                print("changed")
                            }
                            
                        }catch let jsonErr{
                            print("Error serializing json:" ,jsonErr)
                        }
                    }
                    else{
                        print("error")
                    }
                    }.resume()
            }
        }
        
    }
}
