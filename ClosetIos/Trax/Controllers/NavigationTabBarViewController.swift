//
//  NavigationTabBarViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 12..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class NavigationTabBarViewController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    override func viewWillAppear(_ animated: Bool) {
        if let controllers = viewControllers{
            for controller in controllers{
                if let toiletController = ( controller as? ToiletDataTableViewController )  {
                    print(toiletController.numberOfDataInDatabase)
                    getToilets(for: toiletController)
                }
            }
        }
        if let appdelegate = UIApplication.shared.delegate as? AppDelegate{
            print(appdelegate.userId)
        }
        
    }

    @IBOutlet weak var toiletButton: UITabBar!
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem){
        //TODO: --set the views data from database
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    }
    //selese
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    private func getToilets(for view : UIViewController){
        
        print("a")
    }
//        if let url = URL(string : URLStorage.getToilets){
////            if let target = ( view as? ToiletDataTableViewController ){
////                target.processStarted()
////            }
//            URLSession.shared.dataTask(with: url) { data, response, err in
//                if let data = data{
//                    do{
//
//                        let newToilets = try JSONDecoder().decode([BasicToilet].self, from: data)
//                        print("ok")
//                        DispatchQueue.main.async {
//                            if let target = ( view as? ToiletDataTableViewController ){
//                                target.processTerminated(with:newToilets)
//                                print("asd")
//                            }
////                            self.processTerminated()
////                            self.toilets = newToilets
////                            print("asd")
////                            print(newToilets)
//                        }
//                    }catch let jsonErr{
//                        print("Error serializing json:" ,jsonErr)
//                    }
//                }
//                else{
//                    print("error")
//                }
//                }.resume()
//        }
//
//    }

//}
}
