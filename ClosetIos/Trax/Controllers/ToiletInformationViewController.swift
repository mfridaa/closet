//
//  ToiletInformationViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class ToiletInformationViewController: UIViewController {

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let navController = (segue.destination as? UINavigationController){
            let tableViewController = navController.viewControllers.compactMap({ $0 as? ToiletInformationsTableViewController})
            if let toiletInformationTableViewController = tableViewController.first,tableViewController.count != 1 {
                print("asd")
  
            }
        }
    }

}
